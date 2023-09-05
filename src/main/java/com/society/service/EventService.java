package com.society.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.society.dto.EventDTO;
import com.society.entity.OnlinePayment;
import com.society.exception.EventNotFoundException;
import com.society.exception.LoginException;
import com.society.exception.NotAvailableException;
import com.society.exception.NotFoundException;

public interface EventService {
	
	void checkEventAvailability(String key, String place, LocalDate date, LocalTime startTime, long hours) throws LoginException, NotAvailableException;   
	List<EventDTO> getBookedSlots(String key) throws LoginException;
	EventDTO scheduleEvent(String key, EventDTO eventDTO) throws LoginException, NotAvailableException;
    List<EventDTO> getResidentScheduledEvents(String key) throws LoginException;
	List<EventDTO> getPreviousScheduledEvents(String key) throws LoginException;
	
	List<EventDTO> getAllScheduledEvents(String key) throws LoginException;
	
	EventDTO updateEvent(String key, Long eventId, EventDTO eventDTO) throws LoginException, NotFoundException, NotAvailableException;
	EventDTO getEventById(String key, Long eventId) throws LoginException, NotFoundException;
	void deleteEvent(String key, Long eventId) throws LoginException, NotFoundException;
	//void markEventAsPaid(String key, Long eventId) throws EventNotFoundException, LoginException;
	void markingEventAsPaid(String key, Long eventId, OnlinePayment paymentRequest)throws EventNotFoundException, LoginException;
	List<EventDTO> getCommitteeScheduledEvents(String key) throws LoginException;


	
}

