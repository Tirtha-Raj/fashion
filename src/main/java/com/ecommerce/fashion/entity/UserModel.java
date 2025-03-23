package com.ecommerce.fashion.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class UserModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "Username")
	private String username;
	@Column(name = "Companyname")
	private String companyName;
	@Column(name = "password")
	private String password;
	@Column(name = "email", unique = true)
	private String email;
	@ManyToOne
	@JoinColumn(name = "role", referencedColumnName = "id")
	private RoleModel role;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public RoleModel getRole() {
		return role;
	}

	public void setRole(RoleModel role) {
		this.role = role;
	}

	public UserModel(int id, String username, String companyName, String password, String email, RoleModel role) {
		super();
		this.id = id;
		this.username = username;
		this.companyName = companyName;
		this.password = password;
		this.email = email;
		this.role = role;
	}

	public UserModel() {
		super();
	}

}
