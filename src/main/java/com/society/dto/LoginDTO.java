package com.society.dto;

public class LoginDTO {

	private String email;
	private String password;
	private String role;
	
	public LoginDTO(String email, String password, String role) {
		super();
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public LoginDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "LoginDTO [email=" + email + ", password=" + password + ", role=" + role + "]";
	}

	
	
}

