package com.society.dto;

import java.time.LocalDate;

public class ComplaintReplyDTO {
    private Long replyId;
    private Long cid;
    private Long rId;
    private LocalDate date;
    private String response;
    private String complaintDescription;
    private String solutionMsg;
    private LocalDate complaintDate;
    private String complainerName;
	public Long getReplyId() {
		return replyId;
	}
	public void setReplyId(Long replyId) {
		this.replyId = replyId;
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public Long getrId() {
		return rId;
	}
	public void setrId(Long rId) {
		this.rId = rId;
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
	public ComplaintReplyDTO(Long replyId, Long cid, Long rId, LocalDate date, String response,
			String complaintDescription, String solutionMsg, LocalDate complaintDate, String complainerName) {
		super();
		this.replyId = replyId;
		this.cid = cid;
		this.rId = rId;
		this.date = date;
		this.response = response;
		this.complaintDescription = complaintDescription;
		this.solutionMsg = solutionMsg;
		this.complaintDate = complaintDate;
		this.complainerName = complainerName;
	}
	public ComplaintReplyDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ComplaintReplyDTO [replyId=" + replyId + ", cid=" + cid + ", rId=" + rId + ", date=" + date
				+ ", response=" + response + ", complaintDescription=" + complaintDescription + ", solutionMsg="
				+ solutionMsg + ", complaintDate=" + complaintDate + ", complainerName=" + complainerName + "]";
	}
	
    
	
    

}

