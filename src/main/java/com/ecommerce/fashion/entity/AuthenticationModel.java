package com.ecommerce.fashion.entity;

public class AuthenticationModel {

	private String email;
	private String password;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AuthenticationModel(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
}
