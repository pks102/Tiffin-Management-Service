package com.example.demo.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class User{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "userTypeId"))
    private List<UserType> userType;

	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	private Date createdDate;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = true)
	private Date updatedDate;
	
	@Email
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

	public String getName() {
		return name;
	}

	public List<UserType> getUserType() {
		return userType;
	}

	public void setUserType(List<UserType> userType) {
		this.userType = userType;
	}

	public User() {
		super();
	}

	public User(User users) {
		this.userId=users.getUserId();
		this.createdDate=users.createdDate;
		this.updatedDate=users.updatedDate;
		this.emailId = users.getEmailId();
        this.userType = users.getUserType();
        this.name = users.getName();
        this.userName =users.getUserName();
        this.password = users.getPassword();
        this.address = users.getAddress();
        this.contactNo = users.getContactNo();
        }

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userType=" + userType + ", createdDate=" + createdDate + ", updatedDate="
				+ updatedDate + ", emailId=" + emailId + ", contactNo=" + contactNo + ", address=" + address + ", name="
				+ name + ", userName=" + userName + ", password=" + password + "]";
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

	public Date getCreatedDate() {
		return createdDate;
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

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
}
