package cn.sysu.sse.recruitment.job_platform_api.server.service.impl;

import cn.sysu.sse.recruitment.job_platform_api.common.enums.WorkNature;
import cn.sysu.sse.recruitment.job_platform_api.common.error.BusinessException;
import cn.sysu.sse.recruitment.job_platform_api.common.error.ErrorCode;
import cn.sysu.sse.recruitment.job_platform_api.pojo.dto.LlmJobExtractionDTO;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.City;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.JobCategory;
import cn.sysu.sse.recruitment.job_platform_api.pojo.entity.Province;
import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.JobFormParseResponseVO;
import cn.sysu.sse.recruitment.job_platform_api.server.config.AiDashscopeProperties;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.JobCategoryMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.mapper.LocationMapper;
import cn.sysu.sse.recruitment.job_platform_api.server.service.JobFormParseService;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversation;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationParam;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationResult;
import com.alibaba.dashscope.common.MultiModalMessage;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.exception.UploadFileException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class JobFormParseServiceImpl implements JobFormParseService {

    private static final Logger logger = LoggerFactory.getLogger(JobFormParseServiceImpl.class);

    private static final long MAX_IMAGE_SIZE = 7L * 1024 * 1024;
    private static final String DEFAULT_MODEL = "qwen-vl-plus";
    private static final String DEFAULT_IMAGE_PROMPT = "请解析这张招聘海报";
    private static final String SYSTEM_PROMPT = "你是一个API后端辅助程序。请分析用户提供的招聘信息，提取关键字段。\\n" +
            "严格遵守以下规则：\\n" +
            "1. 仅输出标准 JSON 字符串，严禁包含任何 markdown 标记（如 ```json）。\\n" +
            "2. 薪资统一转换为 k (千元) 为单位的整数。\\n" +
            "3. 省市请提取标准的中文名称，例如'浙江省'、'杭州市'。\\n" +
            "4. 岗位类别请提取最核心的一个关键词。\\n" +
            "5. description/tech_requirements/bonus_points 采用字符串字段，并在文本内部使用 '1. '、'2. ' 的编号形式分点描述，不要输出 JSON 数组。\\n" +
            "6. 如果未找到某字段，返回 null。\\n" +
            "7. 只允许出现以下字段：title, min_salary, max_salary, province_name, city_name, address_detail, work_nature_text, category_keyword, degree_text, required_start_date, deadline, description, tech_requirements, bonus_points, department, headcount。\\n" +
            "8. 输出前再次检查，确保没有多余字段且没有 markdown 标记。";

    private final AiDashscopeProperties properties;
    private final LocationMapper locationMapper;
    private final JobCategoryMapper jobCategoryMapper;
    private final ObjectMapper objectMapper;

    public JobFormParseServiceImpl(AiDashscopeProperties properties,
                                   LocationMapper locationMapper,
                                   JobCategoryMapper jobCategoryMapper,
                                   ObjectMapper objectMapper) {
        this.properties = properties;
        this.locationMapper = locationMapper;
        this.jobCategoryMapper = jobCategoryMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public JobFormParseResult parseJobForm(Integer hrUserId, String inputType, MultipartFile image, String text) {
        String normalizedType = normalizeInputType(inputType);
        if (normalizedType == null) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "input_type 只能为 image 或 text");
        }

        validateInput(normalizedType, image, text);

        try {
            LlmJobExtractionDTO rawDto = invokeDashscope(normalizedType, image, text);
            JobFormParseResponseVO response = mapToResponse(rawDto);
            return new JobFormParseResult(response, true, "提取表单信息成功");
        } catch (JsonProcessingException ex) {
            logger.warn("AI 返回非标准 JSON，无法完整解析", ex);
            JobFormParseResponseVO fallback = new JobFormParseResponseVO();
            fallback.setJobDetails(new JobFormParseResponseVO.JobDetailsVO());
            return new JobFormParseResult(fallback, false, "解析内容不完整，请人工补充");
        } catch (ApiException | NoApiKeyException | UploadFileException ex) {
            logger.error("调用 DashScope 失败", ex);
            throw new BusinessException(ErrorCode.INTERNAL_ERROR, "智能解析调用失败，请稍后重试");
        } catch (IOException ex) {
            logger.error("解析 AI 响应失败", ex);
            throw new BusinessException(ErrorCode.INTERNAL_ERROR, "解析内容读取失败，请稍后重试");
        }
    }

    private String normalizeInputType(String inputType) {
        if (!StringUtils.hasText(inputType)) {
            return null;
        }
        String normalized = inputType.trim().toLowerCase(Locale.ROOT);
        return ("image".equals(normalized) || "text".equals(normalized)) ? normalized : null;
    }

    private void validateInput(String inputType, MultipartFile image, String text) {
        if ("image".equals(inputType)) {
            if (image == null || image.isEmpty()) {
                throw new BusinessException(ErrorCode.BAD_REQUEST, "当 input_type=image 时必须上传 image 文件");
            }
            if (image.getSize() > MAX_IMAGE_SIZE) {
                throw new BusinessException(ErrorCode.BAD_REQUEST, "图片过大，请上传 7MB 以内的图片");
            }
            String contentType = image.getContentType();
            if (contentType == null || !contentType.toLowerCase(Locale.ROOT).startsWith("image/")) {
                throw new BusinessException(ErrorCode.BAD_REQUEST, "不支持的文件格式");
            }
        } else {
            if (!StringUtils.hasText(text)) {
                throw new BusinessException(ErrorCode.BAD_REQUEST, "当 input_type=text 时必须提供 text 参数");
            }
        }
    }

    private LlmJobExtractionDTO invokeDashscope(String inputType, MultipartFile image, String text)
            throws ApiException, NoApiKeyException, UploadFileException, IOException {
        List<MultiModalMessage> messages = buildMessages(inputType, image, text);
        MultiModalConversationParam param = MultiModalConversationParam.builder()
                .model(resolveModelName())
                .apiKey(resolveApiKey())
                .messages(messages)
                .build();

        MultiModalConversation conv = new MultiModalConversation();
        MultiModalConversationResult result = conv.call(param);
        String rawText = extractTextFromResult(result);
        if (!StringUtils.hasText(rawText)) {
            throw new BusinessException(ErrorCode.INTERNAL_ERROR, "智能解析返回内容为空");
        }
        logger.info("[DashScope Response] {}", rawText);
        String cleaned = cleanModelOutput(rawText);
        return objectMapper.readValue(cleaned, LlmJobExtractionDTO.class);
    }

    private String resolveApiKey() {
        if (StringUtils.hasText(properties.getApiKey())) {
            return properties.getApiKey();
        }
        String env = System.getenv("DASHSCOPE_API_KEY");
        if (StringUtils.hasText(env)) {
            return env;
        }
        throw new BusinessException(ErrorCode.INTERNAL_ERROR, "未配置 DashScope API Key");
    }

    private String resolveModelName() {
        return StringUtils.hasText(properties.getModel()) ? properties.getModel() : DEFAULT_MODEL;
    }

    private List<MultiModalMessage> buildMessages(String inputType, MultipartFile image, String text) throws IOException {
        List<MultiModalMessage> messages = new ArrayList<>();
        messages.add(MultiModalMessage.builder()
                .role(Role.SYSTEM.getValue())
                .content(Collections.singletonList(Collections.singletonMap("text", SYSTEM_PROMPT)))
                .build());

        List<Map<String, Object>> userContent = new ArrayList<>();
        if ("image".equals(inputType)) {
            userContent.add(Collections.singletonMap("image", toDataUri(image)));
            if (StringUtils.hasText(text)) {
                userContent.add(Collections.singletonMap("text", text.trim()));
            } else {
                userContent.add(Collections.singletonMap("text", DEFAULT_IMAGE_PROMPT));
            }
        } else {
            userContent.add(Collections.singletonMap("text", text.trim()));
        }

        messages.add(MultiModalMessage.builder()
                .role(Role.USER.getValue())
                .content(userContent)
                .build());
        return messages;
    }

    private String toDataUri(MultipartFile image) throws IOException {
        String contentType = StringUtils.hasText(image.getContentType()) ? image.getContentType() : "image/png";
        String base64 = Base64.getEncoder().encodeToString(image.getBytes());
        return "data:" + contentType + ";base64," + base64;
    }

    private String extractTextFromResult(MultiModalConversationResult result) {
        if (result == null || result.getOutput() == null || CollectionUtils.isEmpty(result.getOutput().getChoices())) {
            return null;
        }
        return result.getOutput().getChoices().stream()
                .filter(Objects::nonNull)
                .map(choice -> choice.getMessage())
                .filter(Objects::nonNull)
                .map(message -> message.getContent())
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .filter(Objects::nonNull)
                .map(entry -> entry.get("text"))
                .filter(Objects::nonNull)
                .map(obj -> obj instanceof String ? (String) obj : null)
                .filter(StringUtils::hasText)
                .findFirst()
                .orElse(null);
    }

    private String cleanModelOutput(String raw) {
        String cleaned = raw
                .replace("```json", "")
                .replace("```", "")
                .trim();
        int start = cleaned.indexOf('{');
        int end = cleaned.lastIndexOf('}');
        if (start >= 0 && end >= start) {
            return cleaned.substring(start, end + 1);
        }
        return cleaned;
    }

    private JobFormParseResponseVO mapToResponse(LlmJobExtractionDTO raw) {
        JobFormParseResponseVO response = new JobFormParseResponseVO();
        JobFormParseResponseVO.JobDetailsVO details = new JobFormParseResponseVO.JobDetailsVO();
        response.setJobDetails(details);
        if (raw == null) {
            return response;
        }

        details.setTitle(raw.getTitle());
        details.setDescription(raw.getDescription());
        details.setTechRequirements(raw.getTechRequirements());
        details.setBonusPoints(raw.getBonusPoints());
        details.setMinSalary(raw.getMinSalary());
        details.setMaxSalary(raw.getMaxSalary());
        details.setDepartment(raw.getDepartment());
        details.setHeadcount(raw.getHeadcount());
        details.setRequiredStartDate(raw.getRequiredStartDate());
        details.setDeadline(raw.getDeadline());
        details.setAddressDetail(raw.getAddressDetail());
        details.setProvinceName(raw.getProvinceName());
        details.setCityName(raw.getCityName());

        mapLocation(raw, details);
        details.setWorkNature(resolveWorkNature(raw.getWorkNatureText()));
        details.setJobCategoryId(resolveJobCategoryId(raw.getCategoryKeyword()));
        details.setRequiredDegree(resolveDegreeCode(raw.getDegreeText()));
        return response;
    }

    private void mapLocation(LlmJobExtractionDTO raw, JobFormParseResponseVO.JobDetailsVO details) {
        if (StringUtils.hasText(raw.getProvinceName())) {
            Optional<Province> provinceOpt = locationMapper.findProvinceByKeyword(raw.getProvinceName().trim());
            provinceOpt.ifPresent(province -> {
                details.setProvinceId(province.getId());
                details.setProvinceName(province.getName());
            });
        }
        if (StringUtils.hasText(raw.getCityName())) {
            Optional<City> cityOpt = locationMapper.findCityByKeyword(raw.getCityName().trim(), details.getProvinceId());
            cityOpt.ifPresent(city -> {
                details.setCityId(city.getId());
                details.setCityName(city.getName());
            });
        }
    }

    private Integer resolveWorkNature(String text) {
        if (!StringUtils.hasText(text)) {
            return null;
        }
        String normalized = text.toLowerCase(Locale.ROOT);
        if (normalized.contains("实习") || normalized.contains("intern")) {
            return WorkNature.INTERNHIP.getCode();
        }
        if (normalized.contains("全职") || normalized.contains("full")) {
            return WorkNature.FULL_TIME.getCode();
        }
        return null;
    }

    private Integer resolveJobCategoryId(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return null;
        }
        JobCategory category = jobCategoryMapper.findByKeyword(keyword.trim());
        if (category == null) {
            category = jobCategoryMapper.findByName(keyword.trim());
        }
        return category != null ? category.getId() : null;
    }

    private Integer resolveDegreeCode(String degreeText) {
        if (!StringUtils.hasText(degreeText)) {
            return null;
        }
        String normalized = degreeText.toLowerCase(Locale.ROOT);
        if (normalized.contains("博士") || normalized.contains("phd")) {
            return 2;
        }
        if (normalized.contains("硕") || normalized.contains("master")) {
            return 1;
        }
        if (normalized.contains("本科") || normalized.contains("学士") || normalized.contains("bachelor")) {
            return 0;
        }
        return null;
    }

}
