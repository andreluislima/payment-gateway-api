package com.api.payment.exception;

import java.time.LocalDateTime;

public class StandardError {

	private LocalDateTime timestamp;
	private Integer status;
	private String error;
	private String message;
	
	public StandardError(LocalDateTime timestap, Integer status, String error, String message) {
		this.timestamp = timestap;
		this.status = status;
		this.error = error;
		this.message = message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public String getError() {
		return error;
	}

	public String getMessage() {
		return message;
	}
	
	
}
