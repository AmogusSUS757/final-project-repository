package com.example.eshopru.exception;


import java.util.HashMap;

import java.util.Map;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(er-> {
			;
			String fieldString = ((FieldError) er).getField();
			String message = ((DefaultMessageSourceResolvable) er).getDefaultMessage();
			errors.put(fieldString, message);
		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
}