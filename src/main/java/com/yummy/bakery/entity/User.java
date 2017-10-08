package com.yummy.bakery.entity;


import java.io.Serializable;


public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private String userId;
	private String email;
	private String userName;
	private String password;
	private boolean enabled = true;
	private UserDetails userDetails;

	public User(){
	}

	public User(String email, String userName, String password, boolean enabled, UserDetails userDetails) {
		this.email = email;
		this.userName = userName;
		this.password = password;
		this.enabled = enabled;
		this.userDetails = userDetails;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}


}