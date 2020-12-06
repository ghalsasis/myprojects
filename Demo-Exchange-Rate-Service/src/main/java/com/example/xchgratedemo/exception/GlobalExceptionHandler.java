package com.example.xchgratedemo.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	private ResponseEntity<ErrorInfo> error(final Exception exception, final HttpStatus httpStatus,
			HttpServletRequest request) {
		return new ResponseEntity<>(new ErrorInfo(exception, request.getRequestURI()), httpStatus);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorInfo> handleRuntimeException(HttpServletRequest request, final RuntimeException e) {
		return error(e, HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(value = {IncorrectApiException.class})
	public ResponseEntity<ErrorInfo> handleIncorrectApiException(HttpServletRequest request,
			IncorrectApiException e) {
		return new ResponseEntity<>(new ErrorInfo(e, request.getRequestURI()), HttpStatus.BAD_REQUEST);
	}
}
