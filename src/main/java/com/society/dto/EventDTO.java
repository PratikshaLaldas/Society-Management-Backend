package com.society.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.society.entity.CommitteeMember;

public class EventDTO {
	
	private Long eventId;
	private Long rId;
	private Long aId;
    private String eventName;
    private String place;
    private Long amount;
    private LocalDate date;
    private LocalTime startTime; // Start time of the event
    private LocalTime endTime;   // End time of the event
    private int hours;
    private String description;
    private String organizerName;
    private String status;
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	public Long getrId() {
		return rId;
	}
	public void setrId(Long rId) {
		this.rId = rId;
	}
	public Long getaId() {
		return aId;
	}
	public void setaId(Long aId) {
		this.aId = aId;
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
	public EventDTO(Long eventId, Long rId, Long aId, String eventName, String place, Long amount, LocalDate date,
			LocalTime startTime, LocalTime endTime, int hours, String description, String organizerName,
			String status) {
		super();
		this.eventId = eventId;
		this.rId = rId;
		this.aId = aId;
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
	public EventDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "EventDTO [eventId=" + eventId + ", rId=" + rId + ", aId=" + aId + ", eventName=" + eventName
				+ ", place=" + place + ", amount=" + amount + ", date=" + date + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", hours=" + hours + ", description=" + description + ", organizerName="
				+ organizerName + ", status=" + status + "]";
	}
    
	
}
