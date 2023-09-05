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
@Table(name = "Suggestion")
public class Suggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sid;

    @ManyToOne
    @JoinColumn(name = "rId")
    private Resident resident;

    private String suggestionerName;
    private LocalDate date;
    private String description;
	public Long getSid() {
		return sid;
	}
	public void setSid(Long sid) {
		this.sid = sid;
	}
	public Resident getResident() {
		return resident;
	}
	public void setResident(Resident resident) {
		this.resident = resident;
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
	public Suggestion(Long sid, Resident resident, String suggestionerName, LocalDate date, String description) {
		super();
		this.sid = sid;
		this.resident = resident;
		this.suggestionerName = suggestionerName;
		this.date = date;
		this.description = description;
	}
	public Suggestion() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Suggestion [sid=" + sid + ", resident=" + resident + ", suggestionerName=" + suggestionerName
				+ ", date=" + date + ", description=" + description + "]";
	}

    
}