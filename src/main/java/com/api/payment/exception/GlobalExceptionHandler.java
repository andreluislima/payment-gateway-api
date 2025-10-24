package com.api.payment.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.auth0.jwt.exceptions.TokenExpiredException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<StandardError>handleBadCredentials(BadCredentialsException ex){
		StandardError error = new  StandardError(
				LocalDateTime.now(),
				HttpStatus.UNAUTHORIZED.value(),
				"Acesso negado",
				"CPF ou senha incorretos"
				);
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	}
	
	@ExceptionHandler(TokenExpiredException.class)
	public ResponseEntity<StandardError> handleTokenExpired(TokenExpiredException ex){
		StandardError error = new StandardError(
				LocalDateTime.now(),
				HttpStatus.UNAUTHORIZED.value(),
				"Token Expirado",
				"Seu token de acesso expirou. Faça login novamente."
			);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError>handleResourceNotFound(ResourceNotFoundException ex){
		StandardError error = new StandardError(
				LocalDateTime.now(),
				HttpStatus.BAD_REQUEST.value(),
				"Erro na operação",
				ex.getMessage()
				);
				
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
     
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError>handleValidation(MethodArgumentNotValidException ex){
		StandardError error = new StandardError(
				LocalDateTime.now(),
				HttpStatus.BAD_REQUEST.value(),
				"Erro de Validação",
				ex.getBindingResult().getFieldError().getDefaultMessage()
			);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	
}
