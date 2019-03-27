package com.infostretch.tiffin.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class UserType {
	@Override
	public String toString() {
		return "UserType [userTypeId=" + userTypeId + ", userTypeName=" + userTypeName + "]";
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int userTypeId;
	@Size(max=10)
	private String userTypeName;

	public UserType() {
		super();
	}
	
	public int getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(int userTypeId) {
		this.userTypeId = userTypeId;
	}

	public String getUserTypeName() {
		return userTypeName;
	}

	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}
}