package cn.sysu.sse.recruitment.job_platform_api.server.handler;

import cn.sysu.sse.recruitment.job_platform_api.common.result.ApiResponse;
import cn.sysu.sse.recruitment.job_platform_api.common.error.BusinessException;
import cn.sysu.sse.recruitment.job_platform_api.common.error.ErrorCode;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(BusinessException.class)
	public ApiResponse<Void> handleBusiness(BusinessException ex) {
		logger.debug("业务异常：code={}, message={}", ex.getCode(), ex.getMessage());
		return ApiResponse.error(ex.getCode(), ex.getMessage());
	}

	@ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
	public ApiResponse<Void> handleValidation(Exception ex) {
		String msg;
		if (ex instanceof MethodArgumentNotValidException manv) {
			msg = manv.getBindingResult().getFieldErrors().stream()
					.map(this::formatFieldError)
					.collect(Collectors.joining("; "));
		} else {
			BindException be = (BindException) ex;
			msg = be.getBindingResult().getFieldErrors().stream()
					.map(this::formatFieldError)
					.collect(Collectors.joining("; "));
		}
		return ApiResponse.error(ErrorCode.BAD_REQUEST.getCode(), msg.isBlank() ? ErrorCode.BAD_REQUEST.getDefaultMessage() : msg);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ApiResponse<Void> handleConstraint(ConstraintViolationException ex) {
		String msg = ex.getConstraintViolations().stream()
				.map(ConstraintViolation::getMessage)
				.collect(Collectors.joining("; "));
		return ApiResponse.error(ErrorCode.BAD_REQUEST.getCode(), msg.isBlank() ? ErrorCode.BAD_REQUEST.getDefaultMessage() : msg);
	}

	@ExceptionHandler({MissingServletRequestParameterException.class, HttpMessageNotReadableException.class, IllegalArgumentException.class})
	public ApiResponse<Void> handleBadRequest(Exception ex) {
		return ApiResponse.error(ErrorCode.BAD_REQUEST.getCode(), ex.getMessage() == null ? ErrorCode.BAD_REQUEST.getDefaultMessage() : ex.getMessage());
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ApiResponse<Void> handleForbidden(AccessDeniedException ex) {
		return ApiResponse.error(ErrorCode.FORBIDDEN.getCode(), ErrorCode.FORBIDDEN.getDefaultMessage());
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ApiResponse<Void> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
		logger.error("数据完整性约束违反", ex);
		String message = "操作失败：数据完整性约束违反";
		// 检查是否是外键约束错误
		if (ex.getMessage() != null && ex.getMessage().contains("foreign key")) {
			message = "无法删除：该记录被其他数据引用，请先删除相关引用记录";
		} else if (ex.getMessage() != null && ex.getMessage().contains("Cannot delete")) {
			message = "无法删除：该记录被其他数据引用，请先删除相关引用记录";
		}
		return ApiResponse.error(ErrorCode.BAD_REQUEST.getCode(), message);
	}

	@ExceptionHandler(Exception.class)
	public ApiResponse<Void> handleOther(Exception ex) {
		logger.error("未处理的异常", ex);
		String message = ex.getMessage() != null ? ex.getMessage() : ErrorCode.INTERNAL_ERROR.getDefaultMessage();
		return ApiResponse.error(ErrorCode.INTERNAL_ERROR.getCode(), message);
	}

	private String formatFieldError(FieldError fe) {
		return fe.getField() + ": " + (fe.getDefaultMessage() == null ? "invalid" : fe.getDefaultMessage());
	}
}
