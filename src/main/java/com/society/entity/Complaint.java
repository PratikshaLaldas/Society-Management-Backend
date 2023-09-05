package com.society.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Complaint")
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;

    @ManyToOne
    @JoinColumn(name = "rId")
    private Resident resident;
    
    private String complainerName;
    
    private LocalDate date;
    private String description;
    private String solutionMsg;
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public Resident getResident() {
		return resident;
	}
	public void setResident(Resident resident) {
		this.resident = resident;
	}
	public String getComplainerName() {
		return complainerName;
	}
	public void setComplainerName(String complainerName) {
		this.complainerName = complainerName;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSolutionMsg() {
		return solutionMsg;
	}
	public void setSolutionMsg(String solutionMsg) {
		this.solutionMsg = solutionMsg;
	}
	public Complaint(Long cid, Resident resident, String complainerName, LocalDate date, String description,
			String solutionMsg) {
		super();
		this.cid = cid;
		this.resident = resident;
		this.complainerName = complainerName;
		this.date = date;
		this.description = description;
		this.solutionMsg = solutionMsg;
	}
	public Complaint() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Complaint [cid=" + cid + ", resident=" + resident + ", complainerName=" + complainerName + ", date="
				+ date + ", description=" + description + ", solutionMsg=" + solutionMsg + "]";
	}
    
	

    
}