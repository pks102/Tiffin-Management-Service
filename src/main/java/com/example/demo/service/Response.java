package com.example.demo.service;

public class Response<T> {
	public String statusCode;
	public T data;	
	public T message;
	public Response(String statusCode,T message,T data) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.data = data;
	}
	public Response(String statusCode,T message) {
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
	
	
	public T getMessage() {
		return message;
	}
	public void setMessage(T message) {
		this.message = message;
	}

	
}
