package com.infostretch.tiffin.model;

import org.springframework.http.HttpStatus;

public class Response<T> {
	private T data;
	private String message;
	private HttpStatus statusCode;
	public Response(T data, String message,HttpStatus status) {
		super();
		this.data = data;
		this.message = message;
		this.statusCode=status;
	}
	public Response(String message,HttpStatus status) {
		super();
		this.message = message;
		this.statusCode=status;
	}
	public HttpStatus getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(HttpStatus status) {
		this.statusCode = status;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
