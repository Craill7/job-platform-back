package cn.sysu.sse.recruitment.job_platform_api.server.handler;

import cn.sysu.sse.recruitment.job_platform_api.common.result.ApiResponse;
import cn.sysu.sse.recruitment.job_platform_api.common.util.BusinessException;
import cn.sysu.sse.recruitment.job_platform_api.common.util.ErrorCode;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
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

	@ExceptionHandler(BusinessException.class)
	public ApiResponse<Void> handleBusiness(BusinessException ex) {
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

	@ExceptionHandler(Exception.class)
	public ApiResponse<Void> handleOther(Exception ex) {
		return ApiResponse.error(ErrorCode.INTERNAL_ERROR.getCode(), ErrorCode.INTERNAL_ERROR.getDefaultMessage());
	}

	private String formatFieldError(FieldError fe) {
		return fe.getField() + ": " + (fe.getDefaultMessage() == null ? "invalid" : fe.getDefaultMessage());
	}
}
