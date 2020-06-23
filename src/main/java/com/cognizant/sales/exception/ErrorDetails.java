package com.cognizant.sales.exception;

import java.util.Date;
/*
 * Default error response provided by Spring Boot contains all the details that are typically needed.
	However, you might want to create a framework independent response structure for your organization
 */
public class ErrorDetails {
	public ErrorDetails(Date timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}
	
	 private Date timestamp;
	 private String message;
	 private String details;
	 
	public Date getTimestamp() {
		return timestamp;
	}
	public String getMessage() {
		return message;
	}
	public String getDetails() {
		return details;
	}
}
