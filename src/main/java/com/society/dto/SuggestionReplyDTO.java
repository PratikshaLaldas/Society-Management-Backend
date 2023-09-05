package com.society.dto;

import java.time.LocalDate;

public class SuggestionReplyDTO {
    private Long replyId;
    private Long sid;
    private Long rId;
    private LocalDate date;
    private String response;
    private LocalDate suggestionDate;
    private String suggestionDescription;
    private String suggestionerName;
	public Long getReplyId() {
		return replyId;
	}
	public void setReplyId(Long replyId) {
		this.replyId = replyId;
	}
	public Long getSid() {
		return sid;
	}
	public void setSid(Long sid) {
		this.sid = sid;
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
	public LocalDate getSuggestionDate() {
		return suggestionDate;
	}
	public void setSuggestionDate(LocalDate suggestionDate) {
		this.suggestionDate = suggestionDate;
	}
	public String getSuggestionDescription() {
		return suggestionDescription;
	}
	public void setSuggestionDescription(String suggestionDescription) {
		this.suggestionDescription = suggestionDescription;
	}
	public String getSuggestionerName() {
		return suggestionerName;
	}
	public void setSuggestionerName(String suggestionerName) {
		this.suggestionerName = suggestionerName;
	}
	public SuggestionReplyDTO(Long replyId, Long sid, Long rId, LocalDate date, String response,
			LocalDate suggestionDate, String suggestionDescription, String suggestionerName) {
		super();
		this.replyId = replyId;
		this.sid = sid;
		this.rId = rId;
		this.date = date;
		this.response = response;
		this.suggestionDate = suggestionDate;
		this.suggestionDescription = suggestionDescription;
		this.suggestionerName = suggestionerName;
	}
	public SuggestionReplyDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "SuggestionReplyDTO [replyId=" + replyId + ", sid=" + sid + ", rId=" + rId + ", date=" + date
				+ ", response=" + response + ", suggestionDate=" + suggestionDate + ", suggestionDescription="
				+ suggestionDescription + ", suggestionerName=" + suggestionerName + "]";
	}
    
	

   
}

