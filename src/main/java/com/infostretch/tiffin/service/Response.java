package com.infostretch.tiffin.service;

public class Response<T> {
	private String statusCode;
	private T data;	
	private String message;
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
