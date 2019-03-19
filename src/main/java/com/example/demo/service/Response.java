package com.example.demo.service;

public class Response<T> {
	public String statusCode;
	public T data;	

	public Response(String statusCode,String message,T data) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.data = data;
	}
	public Response(String statusCode,String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String message;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	
}
