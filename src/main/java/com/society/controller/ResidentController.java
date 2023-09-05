package com.society.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.society.dto.AccountDTO;
import com.society.dto.ComplaintDTO;
import com.society.dto.ComplaintReplyDTO;
import com.society.dto.EventDTO;
import com.society.dto.ResidentDTO;
import com.society.dto.SuggestionDTO;
import com.society.dto.SuggestionReplyDTO;
import com.society.entity.EventAvailability;
import com.society.entity.OnlinePayment;
import com.society.exception.EventNotFoundException;
import com.society.exception.InvalidScheduleException;
import com.society.exception.LoginException;
import com.society.exception.NotAvailableException;
import com.society.exception.NotFoundException;
import com.society.service.AccountService;
import com.society.service.ComplaintReplyService;
import com.society.service.ComplaintService;
import com.society.service.EventService;
import com.society.service.OnlinePaymentService;
import com.society.service.ResidentService;
import com.society.service.SuggestionReplyService;
import com.society.service.SuggestionService;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/residents")
public class ResidentController {

    private final ResidentService residentService;
    private final ComplaintService complaintService;
    private final SuggestionService suggestionService;
    
    @Autowired
    private ComplaintReplyService complaintReplyService;
    
    @Autowired
    private SuggestionReplyService suggestionReplyService;
    
    @Autowired
    private EventService eventService;
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private OnlinePaymentService onlinePaymentService;


    public ResidentController(ResidentService residentService,ComplaintService complaintService, SuggestionService suggestionService) {
        this.residentService = residentService;
        this.complaintService = complaintService;
        this.suggestionService = suggestionService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerResident(@RequestBody ResidentDTO residentDTO) {
        residentService.registerResident(residentDTO);
        return ResponseEntity.ok("Resident registered successfully");
    }
    

    
    @PostMapping("/forgot_password")
    public ResponseEntity<String> sendForgotPasswordEmail(@RequestBody Map<String, String> requestBody) {
        try {
            String email = requestBody.get("email");
            residentService.sendForgotPasswordEmail(email);
                return ResponseEntity.ok("Password reset email sent successfully.");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with the provided email not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }

    @PutMapping("/update-resident-detail/{rId}")
    public ResponseEntity<String> updateResident(String key,@PathVariable Long rId, @RequestBody ResidentDTO residentDTO) throws LoginException {
        residentService.updateResident(key,rId, residentDTO);
        return ResponseEntity.ok("Resident details updated successfully");
    }
    
    @GetMapping("/view-neighbours")
    public List<ResidentDTO> getAllResidents(String key) throws LoginException {
        return residentService.getAllResidents(key);
    }
    
    //newly added 16th august
    
    @GetMapping("view-my-profile")
    public ResponseEntity<ResidentDTO> getMyProfile(String key) throws LoginException {
        ResidentDTO residentDTO = residentService.getMyProfile(key);
        return ResponseEntity.ok(residentDTO);
    }
    
    //complaint
    
    @PostMapping("/register_complaint")
    public ResponseEntity<String> registerComplaint(String key,@RequestBody ComplaintDTO complaintDTO){
    	try {
            ComplaintDTO createdComplaint = complaintService.registerComplaint(key,complaintDTO);
            return ResponseEntity.ok("Complaint registered successfully");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }   	
       
    }
    
    @PutMapping("/update_complaint/{cid}")
    public ResponseEntity<String> updateComplaint(String key,@PathVariable Long cid, @RequestBody ComplaintDTO complaintDTO) {
        try {
        	complaintService.updateComplaint(key,cid, complaintDTO);
            return ResponseEntity.ok("Complaint updated successfully");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
       

    @DeleteMapping("/delete_complaint/{cid}")
    public ResponseEntity<String> deleteComplaint(String key,@PathVariable Long cid) {     
        try {
        	complaintService.deleteComplaint(key,cid);
            return ResponseEntity.ok("Complaint deleted successfully");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
   
    
    @GetMapping("/view_all_complaints")
    public ResponseEntity<List<ComplaintDTO>> getAllComplaints(String key) {
        try {
            List<ComplaintDTO> complaints = complaintService.getAllComplaints(key);
            return ResponseEntity.ok(complaints);
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    //newly added 18th aug
    @GetMapping("/view_complaint/{cid}")
    public ResponseEntity<ComplaintDTO> getComplaintByCid(@PathVariable Long cid,String key
    ) {
        try {
            ComplaintDTO complaint = complaintService.getComplaintByCid(key, cid);
            return ResponseEntity.ok(complaint);
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/view_complaint_reply")
    public ResponseEntity<List<ComplaintReplyDTO>> getComplaintRepliesForResident(String key) {
        try {
            List<ComplaintReplyDTO> complaintReplies = complaintReplyService.getComplaintRepliesForResident(key);
            return ResponseEntity.ok(complaintReplies);
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
 
    
    //suggestion
    
    @PostMapping("/register_suggestion")
    public ResponseEntity<String> registerSuggestion(String key, @RequestBody SuggestionDTO suggestionDTO) {
        try {
            SuggestionDTO createdSuggestion = suggestionService.registerSuggestion(key, suggestionDTO);
            return ResponseEntity.ok("Suggestion registered successfully");
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update_suggestion/{sid}")
    public ResponseEntity<String> updateSuggestion(String key, @PathVariable Long sid,
            @RequestBody SuggestionDTO suggestionDTO) {
        try {
            suggestionService.updateSuggestion(key, sid, suggestionDTO);
            return ResponseEntity.ok("Suggestion updated successfully");
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete_suggestion/{sid}")
    public ResponseEntity<String> deleteSuggestion(String key, @PathVariable Long sid) throws LoginException {
        try {
            suggestionService.deleteSuggestion(key, sid);
            return ResponseEntity.ok("Suggestion deleted successfully");
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/view_all_suggestions")
    public ResponseEntity<List<SuggestionDTO>> getAllSuggestions(String key) {
        try {
            List<SuggestionDTO> suggestions = suggestionService.getAllSuggestions(key);
            return ResponseEntity.ok(suggestions);
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/view_suggestion_reply")
    public ResponseEntity<List<SuggestionReplyDTO>> getSuggestionRepliesForResident(String key) {
        try {
            List<SuggestionReplyDTO> suggestionReplies = suggestionReplyService.getSuggestionRepliesForResident(key);
            return ResponseEntity.ok(suggestionReplies);
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    //newly added 18th aug 
    @GetMapping("/view_suggestion/{sid}")
    public ResponseEntity<SuggestionDTO> getSuggestionBySid(@PathVariable Long sid, String key) {
        try {
            SuggestionDTO suggestion = suggestionService.getSuggestionBySid(key, sid);
            return ResponseEntity.ok(suggestion);
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    //Event scheduling
    //modified aug 21
    @PostMapping("/check-schedule-availability")
    public ResponseEntity<String> checkEventAvailability(String key,@RequestBody EventAvailability request) throws LoginException {
        try {
            eventService.checkEventAvailability(key,request.getPlace(), request.getDate(), request.getStartTime(), request.getHours());
            return ResponseEntity.ok("Time slot and place are available. You can proceed.");
        } catch (NotAvailableException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The chosen time slot and place are not available.");
        }
    }
    //modified aug 21
    @GetMapping("/view-booked-slots")
    public ResponseEntity<List<EventDTO>> getBookedSlots(String key){
        List<EventDTO> bookedSlots;
		try {
			bookedSlots = eventService.getBookedSlots(key);
	        return ResponseEntity.ok(bookedSlots);
		} catch (LoginException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

    }
    
    @PostMapping("/schedule_event")
    public ResponseEntity<String> scheduleEvent(String key, @RequestBody EventDTO eventDTO) throws NotAvailableException {
        try {
            eventService.scheduleEvent(key, eventDTO);
            return ResponseEntity.ok("Event scheduled successfully.");
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login required.");
        } catch (InvalidScheduleException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid event schedule.");
        }
    }
    
    @PutMapping("/update_event/{eventId}")
    public ResponseEntity<String> updateEvent(@PathVariable Long eventId,String key,@RequestBody EventDTO eventDTO) {
        try {
            eventService.updateEvent(key, eventId, eventDTO);
            return ResponseEntity.ok("Event updated successfully.");
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login required.");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found.");
        } catch (NotAvailableException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid event schedule.");
        }
    }
    
    //newly added 21 aug
    @GetMapping("/view_event/{eventId}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long eventId, String key) {
        try {
            EventDTO event = eventService.getEventById(key, eventId);
            return ResponseEntity.ok(event);
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/delete_event/{eventId}")
    public ResponseEntity<String> deleteEvent(String key,@PathVariable Long eventId) {
        try {
            eventService.deleteEvent(key, eventId);
            return ResponseEntity.ok("Event deleted successfully.");
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login required.");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found.");
        }
    }
    
   @GetMapping("/view_scheduled_event")
    public ResponseEntity<List<EventDTO>> viewScheduledEvents(String key){
    	try {
        List<EventDTO> scheduledEvents= eventService.getResidentScheduledEvents(key);
        return ResponseEntity.ok(scheduledEvents);
    }catch (LoginException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    }
   
   //newly added 21 aug
   @GetMapping("/view-previous-events")
   public ResponseEntity<List<EventDTO>> viewPreviousScheduledEvents(String key) {
       try {
    	   List<EventDTO> scheduledEvents= eventService.getPreviousScheduledEvents(key);
           return ResponseEntity.ok(scheduledEvents);
       } catch (LoginException e) {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }
   }
    
    @GetMapping("/view_committee_event")
    public ResponseEntity<List<EventDTO>> viewCommitteeScheduledEvents(String key) {
    	try {
        List<EventDTO> scheduledEvents= eventService.getCommitteeScheduledEvents(key);
        return ResponseEntity.ok(scheduledEvents);
    }catch (LoginException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    }
    
   
    
    //new
    @PostMapping("/make-event-payment/{eventId}")
    public ResponseEntity<String> markEventAsPaid(@PathVariable Long eventId, @RequestBody OnlinePayment paymentRequest, String key) {
        try {
            eventService.markingEventAsPaid(key, eventId, paymentRequest);
            return ResponseEntity.ok("Event Payment made successfully");
        } catch (EventNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found.");
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login required.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment Failed.Please enter correct amount");
        }
    }
    
//accounting
    
    @GetMapping("/view-my-bill")
    public ResponseEntity<List<AccountDTO>> getMyBills(String key) {
        try {
            List<AccountDTO> bills = accountService.getMyBills(key);
            return ResponseEntity.ok(bills);
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    
    @GetMapping("/view-my-previous-bill")
    public ResponseEntity<List<AccountDTO>> getMyPreviousBills(String key) {
        try {
            List<AccountDTO> bills = accountService.getMyPreviousBills(key);
            return ResponseEntity.ok(bills);
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    
    @PostMapping("/make-online-payment/{billNo}")
    public ResponseEntity<String> makeOnlinePayment(String key,@PathVariable Long billNo, @RequestBody OnlinePayment paymentRequest) {
        try {
            onlinePaymentService.makeOnlinePayment(key, billNo, paymentRequest);
            return ResponseEntity.ok("Payment successful. Thank you");
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login required.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment failed.");
        }
    }

}
