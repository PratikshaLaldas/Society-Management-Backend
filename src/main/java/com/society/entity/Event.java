package com.society.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;
    
    @ManyToOne
    @JoinColumn(name = "rId")
    private Resident resident;
    
    @ManyToOne
    @JoinColumn(name = "aId") 
    private CommitteeMember committeemember;
    
    private String eventName;
    private String place;
    private Long amount;
    private LocalDate date;
    private LocalTime startTime; // Start time of the event
    private LocalTime endTime;   // End time of the event
    private int hours;
    private String description;
    private String organizerName;
    
    
    @Column(columnDefinition = "varchar(50) default 'unpaid'")
    private String status;


	public Long getEventId() {
		return eventId;
	}


	public void setEventId(Long eventId) {
		this.eventId = eventId;
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


	public String getEventName() {
		return eventName;
	}


	public void setEventName(String eventName) {
		this.eventName = eventName;
	}


	public String getPlace() {
		return place;
	}


	public void setPlace(String place) {
		this.place = place;
	}


	public Long getAmount() {
		return amount;
	}


	public void setAmount(Long amount) {
		this.amount = amount;
	}


	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}


	public LocalTime getStartTime() {
		return startTime;
	}


	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}


	public LocalTime getEndTime() {
		return endTime;
	}


	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}


	public int getHours() {
		return hours;
	}


	public void setHours(int hours) {
		this.hours = hours;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getOrganizerName() {
		return organizerName;
	}


	public void setOrganizerName(String organizerName) {
		this.organizerName = organizerName;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Event(Long eventId, Resident resident, CommitteeMember committeemember, String eventName, String place,
			Long amount, LocalDate date, LocalTime startTime, LocalTime endTime, int hours, String description,
			String organizerName, String status) {
		super();
		this.eventId = eventId;
		this.resident = resident;
		this.committeemember = committeemember;
		this.eventName = eventName;
		this.place = place;
		this.amount = amount;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.hours = hours;
		this.description = description;
		this.organizerName = organizerName;
		this.status = status;
	}


	public Event() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "Event [eventId=" + eventId + ", resident=" + resident + ", committeemember=" + committeemember
				+ ", eventName=" + eventName + ", place=" + place + ", amount=" + amount + ", date=" + date
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", hours=" + hours + ", description="
				+ description + ", organizerName=" + organizerName + ", status=" + status + "]";
	}


	

	
	
	
	

    
}