package com.society.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class CurrentUserSession {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer currSessionId;
	
	private String email;	

	private LocalDateTime loginDateTime;
	
	private String role;

	private String privateKey;
	
	@ManyToOne
    @JoinColumn(name = "rId") 
    private Resident resident;
	
	@ManyToOne
    @JoinColumn(name = "aId") 
    private CommitteeMember committeemember;

	public Integer getCurrSessionId() {
		return currSessionId;
	}

	public void setCurrSessionId(Integer currSessionId) {
		this.currSessionId = currSessionId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getLoginDateTime() {
		return loginDateTime;
	}

	public void setLoginDateTime(LocalDateTime loginDateTime) {
		this.loginDateTime = loginDateTime;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public Resident getResident() {
		return resident;
	}

	public void setResident(Resident resident) {
		this.resident = resident;
	}

	public CommitteeMember getCommitteemember() {
		return committeemember;
	}

	public void setCommitteemember(CommitteeMember committeemember) {
		this.committeemember = committeemember;
	}

	public CurrentUserSession(Integer currSessionId, String email, LocalDateTime loginDateTime, String role,
			String privateKey, Resident resident, CommitteeMember committeemember) {
		super();
		this.currSessionId = currSessionId;
		this.email = email;
		this.loginDateTime = loginDateTime;
		this.role = role;
		this.privateKey = privateKey;
		this.resident = resident;
		this.committeemember = committeemember;
	}

	public CurrentUserSession() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "CurrentUserSession [currSessionId=" + currSessionId + ", email=" + email + ", loginDateTime="
				+ loginDateTime + ", role=" + role + ", privateKey=" + privateKey + ", resident=" + resident
				+ ", committeemember=" + committeemember + "]";
	}

	
	
}