package com.society.dto;

import java.time.LocalDate;

public class SuggestionDTO {
    private Long sid;
    private Long rId;
    private String suggestionerName;
    private LocalDate date;
    private String description;
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
	public String getSuggestionerName() {
		return suggestionerName;
	}
	public void setSuggestionerName(String suggestionerName) {
		this.suggestionerName = suggestionerName;
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
	public SuggestionDTO(Long sid, Long rId, String suggestionerName, LocalDate date, String description) {
		super();
		this.sid = sid;
		this.rId = rId;
		this.suggestionerName = suggestionerName;
		this.date = date;
		this.description = description;
	}
	public SuggestionDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "SuggestionDTO [sid=" + sid + ", rId=" + rId + ", suggestionerName=" + suggestionerName + ", date="
				+ date + ", description=" + description + "]";
	}

    
}
