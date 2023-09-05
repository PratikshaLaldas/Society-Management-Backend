package com.society.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.society.dto.EventDTO;
import com.society.entity.CurrentUserSession;
import com.society.entity.Event;
import com.society.entity.OnlinePayment;
import com.society.exception.EventNotFoundException;
import com.society.exception.LoginException;
import com.society.exception.NotAvailableException;
import com.society.exception.NotFoundException;
import com.society.repository.CurrentUserSessionRepository;
import com.society.repository.EventRepository;
import com.society.repository.OnlinePaymentRepository;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CurrentUserSessionRepository currentUserSessionRepository;
    
    @Autowired
    private OnlinePaymentRepository onlinePaymentRepository;
    
    @Override
    public void checkEventAvailability(String key, String place, LocalDate date, LocalTime startTime, long hours) throws LoginException, NotAvailableException {
        
    	CurrentUserSession currentUserSession = currentUserSessionRepository.findByPrivateKey(key);
        if (currentUserSession == null) {
            throw new LoginException("Login required");
        }
        
    	LocalTime endTime = startTime.plusHours(hours);

        List<Event> conflictingEvents = eventRepository.findByPlaceAndDateAndEndTimeGreaterThanAndStartTimeLessThan(
                place, date, startTime, endTime);

        if (!conflictingEvents.isEmpty()) {
            throw new NotAvailableException("The chosen time slot and place are not available");
        }
    }

    
    @Override
    public List<EventDTO> getBookedSlots(String key) throws LoginException {
    	
    	CurrentUserSession currentUserSession = currentUserSessionRepository.findByPrivateKey(key);
        if (currentUserSession == null) {
            throw new LoginException("Login required");
        }
        
        List<Event> bookedEvents = eventRepository.findAll();

        List<EventDTO> bookedSlotDTOs = new ArrayList<>();
        for (Event bookedEvent : bookedEvents) {
            EventDTO bookedSlotDTO = new EventDTO();
            bookedSlotDTO.setOrganizerName(bookedEvent.getOrganizerName());
            bookedSlotDTO.setDate(bookedEvent.getDate());
            bookedSlotDTO.setStartTime(bookedEvent.getStartTime());
            bookedSlotDTO.setEndTime(bookedEvent.getEndTime());
            bookedSlotDTO.setHours(bookedEvent.getHours());
            bookedSlotDTO.setPlace(bookedEvent.getPlace());

            bookedSlotDTOs.add(bookedSlotDTO);
        }

        return bookedSlotDTOs;
    }

    @Override
    public EventDTO scheduleEvent(String key, EventDTO eventDTO) throws LoginException, NotAvailableException {
        CurrentUserSession currentUserSession = currentUserSessionRepository.findByPrivateKey(key);
        if (currentUserSession == null) {
            throw new LoginException("Login required");
        }

        // Check if the chosen place and time slot are available
        LocalDate eventDate = eventDTO.getDate();
        LocalTime eventStartTime = eventDTO.getStartTime();
        long hours = eventDTO.getHours(); // Assuming eventDTO.getHours() returns the user-defined hours
        LocalTime eventEndTime = eventStartTime.plusHours(hours);

        /*recent new
        List<Event> conflictingEvents = eventRepository.findByPlaceAndDateAndStartTimeBetween(
        	    eventDTO.getPlace(), eventDate, eventStartTime, eventEndTime.plusSeconds(1));

        	if (!conflictingEvents.isEmpty()) {
        	    throw new NotAvailableException("The chosen time slot and place are not available");
        	}
*/
        List<Event> conflictingEvents = eventRepository.findByPlaceAndDateAndEndTimeGreaterThanAndStartTimeLessThan(
        	    eventDTO.getPlace(), eventDate, eventStartTime, eventEndTime);

        	if (!conflictingEvents.isEmpty()) {
        	    throw new NotAvailableException("The chosen time slot and place are not available");
        	}

        
     // Calculate duration and amount based on place
        Long baseAmount = eventDTO.getPlace().equalsIgnoreCase("hall") ? 1000L : 1100L; // Assuming amounts are in Long
        Long calculatedAmount = baseAmount * eventDTO.getHours();

       	eventDTO.setAmount(calculatedAmount);
       	
        
        String eventOrganizerName = currentUserSession.getResident().getfName() + " " + currentUserSession.getResident().getlName();


        // Set resident and default status
        Event event = new Event();
        event.setResident(currentUserSession.getResident());        
        event.setEventName(eventDTO.getEventName());
        event.setOrganizerName(eventOrganizerName);
        event.setDescription(eventDTO.getDescription());
        event.setPlace(eventDTO.getPlace());
        event.setDate(eventDTO.getDate());
        event.setStartTime(eventDTO.getStartTime());
        event.setEndTime(eventEndTime);
        event.setHours(eventDTO.getHours());
        event.setAmount(eventDTO.getAmount());
        event.setStatus("unpaid");
        
        Event savedEvent = eventRepository.save(event);   
        eventDTO.setEventId(savedEvent.getEventId());
        // Save the event
        
        return eventDTO;
    }
    
    
    @Override
    public EventDTO updateEvent(String key, Long eventId, EventDTO eventDTO) throws LoginException, NotFoundException, NotAvailableException {
        CurrentUserSession currentUserSession = currentUserSessionRepository.findByPrivateKey(key);
        if (currentUserSession == null) {
            throw new LoginException("Login required");
        }

        Event existingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found"));

        if (!existingEvent.getResident().getrId().equals(currentUserSession.getResident().getrId())) {
            throw new NotFoundException("Event not found");
        }

        // Check if the chosen place and time slot are available
        LocalDate eventDate = eventDTO.getDate();
        LocalTime eventStartTime = eventDTO.getStartTime();
        long hours = eventDTO.getHours(); // Assuming eventDTO.getHours() returns the user-defined hours
        LocalTime eventEndTime = eventStartTime.plusHours(hours);

        List<Event> conflictingEvents = eventRepository.findByPlaceAndDateAndEndTimeGreaterThanAndStartTimeLessThan(
                eventDTO.getPlace(), eventDate, eventStartTime, eventEndTime);

        conflictingEvents.remove(existingEvent); // Remove the existing event from conflicting events
        if (!conflictingEvents.isEmpty()) {
            throw new NotAvailableException("The chosen time slot and place are not available");
        }

        
     // Calculate duration and amount based on place and hours
        Long baseAmount = eventDTO.getPlace().equalsIgnoreCase("hall") ? 1000L : 1100L; // Assuming amounts are in Long
        Long calculatedAmount = baseAmount * eventDTO.getHours();

        // Update event details
        existingEvent.setEventName(eventDTO.getEventName());
       existingEvent.setOrganizerName(eventDTO.getOrganizerName());
        existingEvent.setDescription(eventDTO.getDescription());
        existingEvent.setPlace(eventDTO.getPlace());
        existingEvent.setDate(eventDate);
        existingEvent.setStartTime(eventStartTime);
        existingEvent.setEndTime(eventEndTime);
        existingEvent.setHours(eventDTO.getHours());
        existingEvent.setAmount(calculatedAmount);

        Event updatedEvent = eventRepository.save(existingEvent);

        // Map and return updated event details
        EventDTO updatedEventDTO = new EventDTO();
        updatedEventDTO.setEventId(updatedEvent.getEventId());
        updatedEventDTO.setrId(updatedEvent.getResident().getrId());
        updatedEventDTO.setOrganizerName(updatedEvent.getOrganizerName());
        updatedEventDTO.setEventName(updatedEvent.getEventName());
        updatedEventDTO.setDescription(updatedEvent.getDescription());
        updatedEventDTO.setPlace(updatedEvent.getPlace());
        updatedEventDTO.setDate(updatedEvent.getDate());
        updatedEventDTO.setStartTime(updatedEvent.getStartTime());
        updatedEventDTO.setEndTime(eventEndTime);
        updatedEventDTO.setHours(updatedEvent.getHours());
        updatedEventDTO.setAmount(updatedEvent.getAmount());
        updatedEventDTO.setStatus(updatedEvent.getStatus());

        return updatedEventDTO;
    }

    public EventDTO getEventById(String key, Long eventId) throws LoginException, NotFoundException {
        CurrentUserSession currentUserSession = currentUserSessionRepository.findByPrivateKey(key);
        if (currentUserSession == null) {
            throw new LoginException("Login required");
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found"));

        // Map Event attributes to EventDTO
        EventDTO eventDTO = new EventDTO();
        eventDTO.setEventId(event.getEventId());
        eventDTO.setrId(event.getResident().getrId());
        eventDTO.setEventName(event.getEventName());
        eventDTO.setDescription(event.getDescription());
        eventDTO.setPlace(event.getPlace());
        eventDTO.setOrganizerName(event.getOrganizerName());
        eventDTO.setDate(event.getDate());
        eventDTO.setStartTime(event.getStartTime());
        eventDTO.setEndTime(event.getEndTime());
        eventDTO.setHours(event.getHours());
        eventDTO.setAmount(event.getAmount());
        eventDTO.setStatus(event.getStatus());

        return eventDTO;
    }
    
    @Override
    public void deleteEvent(String key, Long eventId) throws LoginException, NotFoundException {
        CurrentUserSession currentUserSession = currentUserSessionRepository.findByPrivateKey(key);
        if (currentUserSession == null) {
            throw new LoginException("Login required");
        }

        Event existingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found"));

        if (!existingEvent.getResident().getrId().equals(currentUserSession.getResident().getrId())) {
            throw new NotFoundException("Event not found");
        }

        eventRepository.delete(existingEvent);
    }

    
    
    @Override
    public List<EventDTO> getResidentScheduledEvents(String key) throws LoginException {
        CurrentUserSession currentUserSession = currentUserSessionRepository.findByPrivateKey(key);
        if (currentUserSession == null) {
            throw new LoginException("Login required");
        }

        Long residentId = currentUserSession.getResident().getrId();
        List<Event> scheduledEvents = eventRepository.findByResident_rId(residentId);

        List<EventDTO> eventDTOs = new ArrayList<>();
        for (Event scheduledEvent : scheduledEvents) {
        	if ("unpaid".equals(scheduledEvent.getStatus())) {
            EventDTO eventDTO = new EventDTO();
            // Map the attributes from Event to EventDTO
            eventDTO.setEventId(scheduledEvent.getEventId());
            eventDTO.setrId(scheduledEvent.getResident().getrId());
            eventDTO.setOrganizerName(scheduledEvent.getOrganizerName());
            eventDTO.setEventName(scheduledEvent.getEventName());
            eventDTO.setDescription(scheduledEvent.getDescription());
            eventDTO.setPlace(scheduledEvent.getPlace());
            eventDTO.setDate(scheduledEvent.getDate());
            eventDTO.setStartTime(scheduledEvent.getStartTime());
            eventDTO.setEndTime(scheduledEvent.getEndTime());
            eventDTO.setHours(scheduledEvent.getHours());
            eventDTO.setAmount(scheduledEvent.getAmount());
            eventDTO.setStatus(scheduledEvent.getStatus());
           

            eventDTOs.add(eventDTO);
        }
        }
        return eventDTOs;
    }
    
    @Override
    public List<EventDTO> getPreviousScheduledEvents(String key) throws LoginException {
        CurrentUserSession currentUserSession = currentUserSessionRepository.findByPrivateKey(key);
        if (currentUserSession == null) {
            throw new LoginException("Login required");
        }

        Long residentId = currentUserSession.getResident().getrId();
        List<Event> scheduledEvents = eventRepository.findByResident_rId(residentId);

        List<EventDTO> eventDTOs = new ArrayList<>();
        for (Event scheduledEvent : scheduledEvents) {
        	if ("paid".equals(scheduledEvent.getStatus())) {
            EventDTO eventDTO = new EventDTO();
            // Map the attributes from Event to EventDTO
            eventDTO.setEventId(scheduledEvent.getEventId());
            eventDTO.setrId(scheduledEvent.getResident().getrId());
            eventDTO.setOrganizerName(scheduledEvent.getOrganizerName());
            eventDTO.setEventName(scheduledEvent.getEventName());
            eventDTO.setDescription(scheduledEvent.getDescription());
            eventDTO.setPlace(scheduledEvent.getPlace());
            eventDTO.setDate(scheduledEvent.getDate());
            eventDTO.setStartTime(scheduledEvent.getStartTime());
            eventDTO.setEndTime(scheduledEvent.getEndTime());
            eventDTO.setHours(scheduledEvent.getHours());
            eventDTO.setAmount(scheduledEvent.getAmount());
            eventDTO.setStatus(scheduledEvent.getStatus());
           

            eventDTOs.add(eventDTO);
        }
        }
        return eventDTOs;
    }
    
    @Override
    public void markingEventAsPaid(String key, Long eventId, OnlinePayment paymentRequest) throws EventNotFoundException, LoginException {
        CurrentUserSession currentUserSession = currentUserSessionRepository.findByPrivateKey(key);
        if (currentUserSession == null) {
            throw new LoginException("Login required");
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + eventId));

        if (!event.getResident().getrId().equals(currentUserSession.getResident().getrId())) {
            throw new EventNotFoundException("Event not found with ID: " + eventId);
        }

        // Only allow marking the event as paid if it belongs to the current resident
        if (!event.getResident().equals(currentUserSession.getResident())) {
            throw new EventNotFoundException("Event not found with ID: " + eventId);
        }

        // Create an OnlinePayment entity with user-provided details
        OnlinePayment onlinePayment = new OnlinePayment();
        onlinePayment.setResident(event.getResident());
        onlinePayment.setStreet(paymentRequest.getStreet());
        onlinePayment.setCity(paymentRequest.getCity());
        onlinePayment.setCountry(paymentRequest.getCountry());
        onlinePayment.setZipcode(paymentRequest.getZipcode());
        onlinePayment.setAmount(paymentRequest.getAmount());

        // Validate amount
        if (!onlinePayment.getAmount().equals(event.getAmount())) {
            throw new RuntimeException("Amount is incorrect for this event. Please enter correct amount.");
        }

        // Save the OnlinePayment entity
        onlinePaymentRepository.save(onlinePayment);

        // Update event status to "paid"
        event.setStatus("paid");
        eventRepository.save(event);
    }

    @Override
    public List<EventDTO> getCommitteeScheduledEvents(String key) throws LoginException {
        CurrentUserSession currentUserSession = currentUserSessionRepository.findByPrivateKey(key);
        if (currentUserSession == null) {
            throw new LoginException("Login required");
        }

        List<Event> allScheduledEvents = eventRepository.findAll();

        List<EventDTO> eventDTOs = new ArrayList<>();
        for (Event scheduledEvent : allScheduledEvents) {
        	if (scheduledEvent.getCommitteemember() != null && scheduledEvent.getCommitteemember().getaId()!= null) {
            EventDTO eventDTO = new EventDTO();
            // Map the attributes from Event to EventDTO
            eventDTO.setEventId(scheduledEvent.getEventId());
            eventDTO.setaId(scheduledEvent.getCommitteemember().getaId());
            eventDTO.setOrganizerName(scheduledEvent.getOrganizerName());
            eventDTO.setEventName(scheduledEvent.getEventName());
            eventDTO.setDescription(scheduledEvent.getDescription());
            eventDTO.setPlace(scheduledEvent.getPlace());
            eventDTO.setDate(scheduledEvent.getDate());
            eventDTO.setStartTime(scheduledEvent.getStartTime());
            eventDTO.setEndTime(scheduledEvent.getEndTime());
            eventDTO.setHours(scheduledEvent.getHours());

            eventDTOs.add(eventDTO);
        }
        }

        return eventDTOs;
    }


    //admin side event
    @Override
    public List<EventDTO> getAllScheduledEvents(String key) throws LoginException {
        CurrentUserSession currentUserSession = currentUserSessionRepository.findByPrivateKey(key);
        if (currentUserSession == null) {
            throw new LoginException("Login required");
        }

        List<Event> allScheduledEvents = eventRepository.findAll();

        List<EventDTO> eventDTOs = new ArrayList<>();
        for (Event scheduledEvent : allScheduledEvents) {
        	if (scheduledEvent.getResident() != null && scheduledEvent.getResident().getrId() != null) {
            EventDTO eventDTO = new EventDTO();
            // Map the attributes from Event to EventDTO
            eventDTO.setEventId(scheduledEvent.getEventId());
            eventDTO.setrId(scheduledEvent.getResident().getrId());
            eventDTO.setOrganizerName(scheduledEvent.getOrganizerName());
            eventDTO.setEventName(scheduledEvent.getEventName());
            eventDTO.setDescription(scheduledEvent.getDescription());
            eventDTO.setPlace(scheduledEvent.getPlace());
            eventDTO.setDate(scheduledEvent.getDate());
            eventDTO.setStartTime(scheduledEvent.getStartTime());
            eventDTO.setEndTime(scheduledEvent.getEndTime());
            eventDTO.setHours(scheduledEvent.getHours());
            eventDTO.setAmount(scheduledEvent.getAmount());
            eventDTO.setStatus(scheduledEvent.getStatus());

            eventDTOs.add(eventDTO);
        }
        }

        return eventDTOs;
    }





	

}
