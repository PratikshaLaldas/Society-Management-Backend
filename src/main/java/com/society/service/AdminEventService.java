package com.society.service;

import java.util.List;

import com.society.dto.EventDTO;
import com.society.entity.OnlinePayment;
import com.society.exception.EventNotFoundException;
import com.society.exception.LoginException;
import com.society.exception.NotAvailableException;
import com.society.exception.NotFoundException;

public interface AdminEventService {
	
	List<EventDTO> getBookedSlots(String key) throws LoginException;
	EventDTO adminScheduleEvent(String key, EventDTO eventDTO) throws LoginException, NotAvailableException;
	List<EventDTO> getAdminScheduledEvents(String key) throws LoginException;
	EventDTO getEventById(String key, Long eventId) throws LoginException, NotFoundException;
	EventDTO adminUpdateEvent(String key, Long eventId, EventDTO eventDTO) throws LoginException, NotFoundException, NotAvailableException;
	void adminDeleteEvent(String key, Long eventId) throws LoginException, NotFoundException;
	//void adminMarkEventAsPaid(String key, Long eventId) throws EventNotFoundException, LoginException;
	void adminMarkingEventAsPaid(String key, Long eventId, OnlinePayment paymentRequest) throws EventNotFoundException, LoginException;
	


}
