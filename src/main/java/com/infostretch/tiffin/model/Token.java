package com.infostretch.tiffin.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Token {

  

	public Token() {
		super();
	}



	public Token(String jwtToken) {
		super();
		this.jwtToken = jwtToken;
	}



	@Override
	public String toString() {
		return "Token [ jwtToken=" + jwtToken + "]";
	}

	@JsonIgnore
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
