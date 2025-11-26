package cn.sysu.sse.recruitment.job_platform_api.server.service;

import cn.sysu.sse.recruitment.job_platform_api.pojo.vo.JobFormParseResponseVO;
import org.springframework.web.multipart.MultipartFile;

public interface JobFormParseService {

    JobFormParseResult parseJobForm(Integer hrUserId, String inputType, MultipartFile image, String text);

    class JobFormParseResult {
        private final JobFormParseResponseVO response;
        private final boolean successful;
        private final String message;

        public JobFormParseResult(JobFormParseResponseVO response, boolean successful, String message) {
            this.response = response;
            this.successful = successful;
            this.message = message;
        }

        public JobFormParseResponseVO getResponse() {
            return response;
        }

        public boolean isSuccessful() {
            return successful;
        }

        public String getMessage() {
            return message;
        }
    }
}
