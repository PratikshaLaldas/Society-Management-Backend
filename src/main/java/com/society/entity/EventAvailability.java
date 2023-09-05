package com.society.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventAvailability {
    private String place;
    private LocalDate date;
    private LocalTime startTime;
    private long hours;
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
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
	public long getHours() {
		return hours;
	}
	public void setHours(long hours) {
		this.hours = hours;
	}
	public EventAvailability(String place, LocalDate date, LocalTime startTime, long hours) {
		super();
		this.place = place;
		this.date = date;
		this.startTime = startTime;
		this.hours = hours;
	}
	public EventAvailability() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "EventAvailability [place=" + place + ", date=" + date + ", startTime=" + startTime + ", hours=" + hours
				+ "]";
	}

    
}

