package com.society.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SuggestionReply")
public class SuggestionReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;

    @ManyToOne
    @JoinColumn(name = "sid")
    private Suggestion suggestion;

    @ManyToOne
    @JoinColumn(name = "rId")
    private Resident resident; 

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
	public Suggestion getSuggestion() {
		return suggestion;
	}
	public void setSuggestion(Suggestion suggestion) {
		this.suggestion = suggestion;
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
	public SuggestionReply(Long replyId, Suggestion suggestion, Resident resident, LocalDate date, String response,
			LocalDate suggestionDate, String suggestionDescription, String suggestionerName) {
		super();
		this.replyId = replyId;
		this.suggestion = suggestion;
		this.resident = resident;
		this.date = date;
		this.response = response;
		this.suggestionDate = suggestionDate;
		this.suggestionDescription = suggestionDescription;
		this.suggestionerName = suggestionerName;
	}
	public SuggestionReply() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "SuggestionReply [replyId=" + replyId + ", suggestion=" + suggestion + ", resident=" + resident
				+ ", date=" + date + ", response=" + response + ", suggestionDate=" + suggestionDate
				+ ", suggestionDescription=" + suggestionDescription + ", suggestionerName=" + suggestionerName + "]";
	}
    
	
    
}