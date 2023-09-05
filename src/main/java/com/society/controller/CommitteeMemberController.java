package com.society.controller;


import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.society.dto.AccountDTO;
import com.society.dto.CommitteeMemberDTO;
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
import com.society.service.AdminEventService;
import com.society.service.CommitteeMemberService;
import com.society.service.ComplaintReplyService;
import com.society.service.ComplaintService;
import com.society.service.EventService;
import com.society.service.ResidentService;
import com.society.service.SuggestionReplyService;
import com.society.service.SuggestionService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/committee-member")
public class CommitteeMemberController {

    private final CommitteeMemberService committeeMemberService;
    private final ComplaintService complaintService;
    private final SuggestionService suggestionService;
    private final ComplaintReplyService complaintReplyService;
    
    @Autowired
    private SuggestionReplyService suggestionReplyService;
    
    @Autowired
    private EventService eventService;
    
    @Autowired
    private AdminEventService adminEventService;
    
    @Autowired
    private ResidentService residentService;
    
    @Autowired
    private AccountService accountService;

    public CommitteeMemberController(CommitteeMemberService committeeMemberService, ComplaintService complaintService,SuggestionService suggestionService,ComplaintReplyService complaintReplyService) {
        this.committeeMemberService = committeeMemberService;
        this.complaintService = complaintService;
        this.suggestionService = suggestionService;
        this.complaintReplyService = complaintReplyService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerCommitteeMember(@RequestBody CommitteeMemberDTO committeeMemberDTO) {
        committeeMemberService.registerCommitteeMember(committeeMemberDTO);
        return ResponseEntity.ok("Committee Member registered successfully");
    }

    @PutMapping("/update_profile/{aId}")
    public ResponseEntity<String> updateCommitteeMember(String key,@PathVariable Long aId,@RequestBody CommitteeMemberDTO committeeMemberDTO) throws LoginException {
        committeeMemberService.updateCommitteeMember(key, aId, committeeMemberDTO);
        return ResponseEntity.ok("Committee Member details updated successfully");
    }
    
 
    
    //newly added 16th august
    
    @GetMapping("/view-my-profile")
    public ResponseEntity<CommitteeMemberDTO> getMyProfile(String key) throws LoginException {
        CommitteeMemberDTO committeeMemberDTO = committeeMemberService.getMyProfile(key);
        return ResponseEntity.ok(committeeMemberDTO);
    }
    
 //complaint
    //modified added try catch 
    @GetMapping("/view_all_complaints")
    public ResponseEntity<List<ComplaintDTO>> getAllComplaints(String key){
    	try {
        List<ComplaintDTO> complaints = complaintReplyService.getAllComplaintsOfAllMember(key);
        return ResponseEntity.ok(complaints);
    	} catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    	
    }
    
    @PostMapping("/add_complaint_reply/{cid}")
    public ResponseEntity<String> addComplaintReply(String key,@PathVariable Long cid, @RequestBody ComplaintReplyDTO complaintReplyDTO) {
        try {
            complaintReplyService.addComplaintReply(key,cid,complaintReplyDTO);
            return ResponseEntity.ok("Complaint reply added successfully.");
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login required.");
        }
    }
    
    @GetMapping("/view_all_complaint_replies")
    public ResponseEntity<List<ComplaintReplyDTO>> getAllComplaintReplies(String key) {
        try {
            List<ComplaintReplyDTO> complaintReplies = complaintReplyService.getAllComplaintReplies(key);
            return ResponseEntity.ok(complaintReplies);
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    //newly added 21st aug
    @GetMapping("/view_complaint_reply/{replyId}")
    public ResponseEntity<ComplaintReplyDTO> getComplaintReplyById(String key,@PathVariable Long replyId) {
    
		try {
			ComplaintReplyDTO complaintReply= complaintReplyService.getComplaintReplyByReplyId(key,replyId);
			return ResponseEntity.ok(complaintReply);
		} catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/update_complaint_reply/{replyId}")
    public ResponseEntity<String> updateComplaintReply(String key, @PathVariable Long replyId, @RequestBody ComplaintReplyDTO complaintReplyDTO) {
        try {
            complaintReplyService.updateComplaintReply(key, replyId, complaintReplyDTO);
            return ResponseEntity.ok("Complaint reply updated successfully.");
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login required.");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Complaint reply not found.");
        }
    }

    @DeleteMapping("/delete_complaint_reply/{replyId}")
    public ResponseEntity<String> deleteComplaintReply(String key, @PathVariable Long replyId) {
        try {
            complaintReplyService.deleteComplaintReply(key, replyId);
            return ResponseEntity.ok("Complaint reply deleted successfully.");
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login required.");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Complaint reply not found.");
        }
    }


//suggestion
    //modified
    @GetMapping("/view_all_suggestions")
    public ResponseEntity<List<SuggestionDTO>> getAllSuggestions(String key){
        List<SuggestionDTO> suggestions;
		try {
			suggestions = suggestionReplyService.getAllSuggestionsOfAllMember(key);
			  return ResponseEntity.ok(suggestions);
		} catch (LoginException e) {
			 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
      
    }
    
    @PostMapping("/add_suggestion_reply/{sid}")
    public ResponseEntity<String> addSuggestionReply(@PathVariable Long sid,String key,@RequestBody SuggestionReplyDTO suggestionReplyDTO) {
        try {
            suggestionReplyService.addSuggestionReply(key, sid, suggestionReplyDTO);
            return ResponseEntity.ok("Suggestion reply added successfully.");
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login required.");
        }
    }


    @GetMapping("/view_all_suggestion_replies")
    public ResponseEntity<List<SuggestionReplyDTO>> getAllSuggestionReplies(String key) {
        try {
            List<SuggestionReplyDTO> suggestionReplies = suggestionReplyService.getAllSuggestionReplies(key);
            return ResponseEntity.ok(suggestionReplies);
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    //added on 21st aug
    @GetMapping("/view_suggestion_reply/{replyId}")
    public ResponseEntity<SuggestionReplyDTO> getSuggestionReplyById(String key, @PathVariable Long replyId) {
        try {
            SuggestionReplyDTO suggestionReply = suggestionReplyService.getSuggestionReplyByReplyId(key, replyId);
            return ResponseEntity.ok(suggestionReply);
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update_suggestion_reply/{replyId}")
    public ResponseEntity<String> updateSuggestionReply(String key, @PathVariable Long replyId,
            @RequestBody SuggestionReplyDTO suggestionReplyDTO) {
        try {
            suggestionReplyService.updateSuggestionReply(key, replyId, suggestionReplyDTO);
            return ResponseEntity.ok("Suggestion reply updated successfully.");
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login required.");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Suggestion reply not found.");
        }
    }

    @DeleteMapping("/delete_suggestion_reply/{replyId}")
    public ResponseEntity<String> deleteSuggestionReply(String key, @PathVariable Long replyId) {
        try {
            suggestionReplyService.deleteSuggestionReply(key, replyId);
            return ResponseEntity.ok("Suggestion reply deleted successfully.");
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login required.");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Suggestion reply not found.");
        }
    }
    
    //event admin side
    
    @PostMapping("/check-schedule-availability")
    public ResponseEntity<String> checkEventAvailability(String key,@RequestBody EventAvailability request) throws LoginException {
        try {
            eventService.checkEventAvailability(key,request.getPlace(), request.getDate(), request.getStartTime(), request.getHours());
            return ResponseEntity.ok("Time slot and place is available. You can proceed.");
        } catch (NotAvailableException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The chosen time slot and place is not available.");
        }
    }
    
    @GetMapping("/view-booked-slots")
    public ResponseEntity<List<EventDTO>> getBookedSlots(String key) {
        List<EventDTO> bookedSlots;
		try {
			bookedSlots = adminEventService.getBookedSlots(key);
			return ResponseEntity.ok(bookedSlots);
		} catch (LoginException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
        
    }
    
    @PostMapping("/schedule_event")
    public ResponseEntity<String> scheduleEvent(String key, @RequestBody EventDTO eventDTO) throws NotAvailableException {
        try {
            adminEventService.adminScheduleEvent(key, eventDTO);
            return ResponseEntity.ok("Event scheduled successfully.");
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login required.");
        } catch (InvalidScheduleException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid event schedule.");
        }
    }
    
    //newly added 22aug
    @GetMapping("/view_event/{eventId}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long eventId, String key) {
        try {
            EventDTO event = adminEventService.getEventById(key, eventId);
            return ResponseEntity.ok(event);
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/view_scheduled_event")
    public ResponseEntity<List<EventDTO>> viewScheduledEvents(String key) {
    	try {
        List<EventDTO> scheduledEvents= adminEventService.getAdminScheduledEvents(key);
        return ResponseEntity.ok(scheduledEvents);
    }catch (LoginException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    }
    
    @PutMapping("/update_event/{eventId}")
    public ResponseEntity<String> updateEvent(@PathVariable Long eventId,String key,@RequestBody EventDTO eventDTO) {
        try {
            adminEventService.adminUpdateEvent(key, eventId, eventDTO);
            return ResponseEntity.ok("Event updated successfully.");
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login required.");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found.");
        } catch (NotAvailableException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The Timeslot and Place is not available");
        }
    }
    
    @DeleteMapping("/delete_event/{eventId}")
    public ResponseEntity<String> deleteEvent(String key,@PathVariable Long eventId) {
        try {
            adminEventService.adminDeleteEvent(key, eventId);
            return ResponseEntity.ok("Event deleted successfully.");
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login required.");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found.");
        }
    }
    

    /* we wonttake charges from admin-21aug
    @PostMapping("/mark-as-paid/{eventId}")
    public ResponseEntity<String> markEventAsPaid(@PathVariable Long eventId, @RequestBody OnlinePayment paymentRequest, String key) {
        try {
            adminEventService.adminMarkingEventAsPaid(key, eventId, paymentRequest);
            return ResponseEntity.ok("Event marked as paid successfully.");
        } catch (EventNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found.");
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login required.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to mark event as paid.");
        }
    }*/
    
    @GetMapping("/view_all_scheduled_events")
    public ResponseEntity<List<EventDTO>> getAllScheduledEvents(String key){
        try {
    	List<EventDTO> scheduledEvent = eventService.getAllScheduledEvents(key);
    	return ResponseEntity.ok(scheduledEvent);
        }catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
 // accounting
    
    @GetMapping("/view-all-resident")
    public ResponseEntity<List<ResidentDTO>> getAllResidents(String key){
        try {
		 List<ResidentDTO> residents= committeeMemberService.getAllResidents(key);
		 return ResponseEntity.ok(residents);
		} catch (LoginException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
    }
    
    
    @PostMapping("/make-bill/{rId}")
    public ResponseEntity<String> makeBill(String key, @PathVariable Long rId, @RequestBody AccountDTO accountDTO) throws LoginException {
        accountService.createBill(key, rId, accountDTO);
        return ResponseEntity.ok("Bill generated successfully");
    }
    
    @GetMapping("/view_bill/{billNo}")
    public ResponseEntity<AccountDTO> getBillById(@PathVariable Long billNo, String key) {
        try {
            AccountDTO bill = accountService.getBillById(key, billNo);
            return ResponseEntity.ok(bill);
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


	@PutMapping("/update-bill/{billId}")
    public ResponseEntity<String> updateBill(String key, @PathVariable Long billId,@RequestBody AccountDTO accountDTO) {
        try {
            accountService.updateBill(key, billId, accountDTO);
            return ResponseEntity.ok("Bill updated successfully");
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete-bill/{billId}")
    public ResponseEntity<String> deleteBill(String key,@PathVariable Long billId) {
        try {
            accountService.deleteBill(key, billId);
            return ResponseEntity.ok("Bill deleted successfully");
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/view-all-bill")
    public ResponseEntity<List<AccountDTO>> getAllBills(String key) {
        try {
            List<AccountDTO> billDTOs = accountService.getAllBills(key);
            return ResponseEntity.ok(billDTOs);
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
