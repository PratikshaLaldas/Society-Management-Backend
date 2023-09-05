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
@Table(name = "ComplaintReply")
public class ComplaintReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;

    @ManyToOne
    @JoinColumn(name = "cid")
    private Complaint complaint;

    @ManyToOne
    @JoinColumn(name = "rId")
    private Resident resident;
    
    private String complainerName;
    
    private LocalDate complaintDate;
    
    private LocalDate date;
    
    private String complaintDescription;
    private String solutionMsg;
    
    @Column(columnDefinition = "varchar(50) default 'pending'")
    private String response;
       

	public Long getReplyId() {
		return replyId;
	}

	public void setReplyId(Long replyId) {
		this.replyId = replyId;
	}

	public Complaint getComplaint() {
		return complaint;
	}

	public void setComplaint(Complaint complaint) {
		this.complaint = complaint;
	}

	public Resident getResident() {
		return resident;
	}

	public void setResident(Resident resident) {
		this.resident = resident;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getComplaintDescription() {
		return complaintDescription;
	}

	public void setComplaintDescription(String complaintDescription) {
		this.complaintDescription = complaintDescription;
	}

	public String getSolutionMsg() {
		return solutionMsg;
	}

	public void setSolutionMsg(String solutionMsg) {
		this.solutionMsg = solutionMsg;
	}

	public LocalDate getComplaintDate() {
		return complaintDate;
	}

	public void setComplaintDate(LocalDate complaintDate) {
		this.complaintDate = complaintDate;
	}

	public String getComplainerName() {
		return complainerName;
	}

	public void setComplainerName(String complainerName) {
		this.complainerName = complainerName;
	}

	public ComplaintReply(Long replyId, Complaint complaint, Resident resident, LocalDate date, String response,
			String complaintDescription, String solutionMsg, LocalDate complaintDate, String complainerName) {
		super();
		this.replyId = replyId;
		this.complaint = complaint;
		this.resident = resident;
		this.date = date;
		this.response = response;
		this.complaintDescription = complaintDescription;
		this.solutionMsg = solutionMsg;
		this.complaintDate = complaintDate;
		this.complainerName = complainerName;
	}

	public ComplaintReply() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ComplaintReply [replyId=" + replyId + ", complaint=" + complaint + ", resident=" + resident + ", date="
				+ date + ", response=" + response + ", complaintDescription=" + complaintDescription + ", solutionMsg="
				+ solutionMsg + ", complaintDate=" + complaintDate + ", complainerName=" + complainerName + "]";
	}
	
    
    
}