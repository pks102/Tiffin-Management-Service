
package com.example.demo.security;

import org.springframework.stereotype.Component;

@Component
public class JwtResponse {
	

	private String token;
	private String type = "Bearer";


	
	
	public JwtResponse() {
		super();
	}

	public JwtResponse(String accessToken) {
		this.token = accessToken;
	}

	public String getAccessToken() {
		return this.token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}
}
