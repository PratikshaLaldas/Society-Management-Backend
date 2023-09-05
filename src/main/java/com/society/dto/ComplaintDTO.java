package com.society.dto;


import java.time.LocalDate;

public class ComplaintDTO {
    private Long cid;
    private Long rId;
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
	public Long getrId() {
		return rId;
	}
	public void setrId(Long rId) {
		this.rId = rId;
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
	public ComplaintDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ComplaintDTO(Long cid, Long rId, String complainerName, LocalDate date, String description,
			String solutionMsg) {
		super();
		this.cid = cid;
		this.rId = rId;
		this.complainerName = complainerName;
		this.date = date;
		this.description = description;
		this.solutionMsg = solutionMsg;
	}
	@Override
	public String toString() {
		return "ComplaintDTO [cid=" + cid + ", rId=" + rId + ", complainerName=" + complainerName + ", date=" + date
				+ ", description=" + description + ", solutionMsg=" + solutionMsg + "]";
	}
    
	
}
