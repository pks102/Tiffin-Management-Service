package com.infostretch.tiffin.dto;

import java.util.List;

import javax.validation.constraints.Size;

import com.infostretch.tiffin.model.Token;
import com.infostretch.tiffin.model.UserType;

public class UserDTO {
	private int userId;

	private List<UserType> userType;

	private String emailId;

	@Size(max = 12, min = 10)
	private String contactNo;

	@Size(max = 255)
	private String address;

	@Size(max = 30)
	private String name;

	@Size(max = 30)
	private String userName;

	@Size(min = 6)
	private String password;

	private Token token;

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public String getName() {
		return name;
	}

	public List<UserType> getUserType() {
		return userType;
	}

	public void setUserType(List<UserType> userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "UserDTO [userId=" + userId + ", userType=" + userType + ", emailId=" + emailId + ", contactNo="
				+ contactNo + ", address=" + address + ", name=" + name + ", userName=" + userName + ", password="
				+ password + ", token=" + token + "]";
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
