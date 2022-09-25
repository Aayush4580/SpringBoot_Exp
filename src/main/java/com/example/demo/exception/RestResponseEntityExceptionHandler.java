package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.entity.ErrorMessage;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@ResponseStatus
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(DepartmentNotFoundException.class)
	public ResponseEntity<ErrorMessage> departmentNotFound(DepartmentNotFoundException exception, WebRequest request) {
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setMessage(exception.getMessage());
		errorMessage.setStatus(HttpStatus.NOT_FOUND);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorMessage> accessDeniedException(AccessDeniedException e) throws AccessDeniedException {
		log.info(e.toString());
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setMessage(e.getMessage());
		errorMessage.setStatus(HttpStatus.NOT_FOUND);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	}
}
