package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Token {

  

	@Override
	public String toString() {
		return "Token [tokenId=" + tokenId + ", jwtToken=" + jwtToken + "]";
	}

	
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	private int tokenId;
	
	private String jwtToken;

	public int getTokenId() {
		return tokenId;
	}

	public void setTokenId(int tokenId) {
		this.tokenId = tokenId;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

}
