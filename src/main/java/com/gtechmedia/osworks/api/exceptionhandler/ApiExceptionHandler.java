package com.gtechmedia.osworks.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.gtechmedia.osworks.domain.exception.DomainException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(DomainException.class)
	public ResponseEntity<Object> handleDomainException(DomainException ex, WebRequest request) {
		var status = HttpStatus.BAD_REQUEST;
		
		var exception = new CustomException();
		exception.setStatus(status.value());
		exception.setTitle(ex.getMessage());
		exception.setDateTime(LocalDateTime.now());
		
		return handleExceptionInternal(ex, exception, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		var fields = new ArrayList<CustomException.Field>();
		
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			String name = ((FieldError) error).getField();
//			String mensagem = error.getDefaultMessage();
			String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			
			fields.add(new CustomException.Field(name, message));
		}
		
		var exception = new CustomException();
		exception.setStatus(status.value());
		exception.setTitle("Um ou mais campos estão inválidos. Preencha corretamente e tente novamente.");
		exception.setDateTime(LocalDateTime.now());
		exception.setFields(fields);
		
		return super.handleExceptionInternal(ex, exception, headers, status, request);
	}
	
}
