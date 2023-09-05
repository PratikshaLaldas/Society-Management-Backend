package com.society.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.society.dto.ComplaintDTO;
import com.society.dto.ComplaintReplyDTO;
import com.society.entity.Complaint;
import com.society.entity.ComplaintReply;
import com.society.entity.CurrentUserSession;
import com.society.entity.Resident;
import com.society.exception.LoginException;
import com.society.exception.NotFoundException;
import com.society.repository.ComplaintReplyRepository;
import com.society.repository.ComplaintRepository;
import com.society.repository.CurrentUserSessionRepository;
import com.society.repository.ResidentRepository;

@Service
public class ComplaintReplyServiceImpl implements ComplaintReplyService {

    @Autowired
    private ComplaintReplyRepository complaintReplyRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private ResidentRepository residentRepository;

    @Autowired
    private CurrentUserSessionRepository currSession;

    /* admin side*/
    
    @Override
    public List<ComplaintDTO> getAllComplaintsOfAllMember(String key) throws LoginException{
    	
    	CurrentUserSession currSess = currSession.findByPrivateKey(key);
		if (currSess == null)
			throw new LoginException("Login required");
		
        List<Complaint> complaints = complaintRepository.findAll();

        List<ComplaintDTO> complaintDTOs = new ArrayList<>();
        for (Complaint complaint : complaints) {
        	if (!complaintReplyRepository.existsByComplaint_Cid(complaint.getCid())) {
            ComplaintDTO complaintDTO = new ComplaintDTO();
            // Map the attributes from Complaint to ComplaintDTO
            complaintDTO.setCid(complaint.getCid());
            complaintDTO.setrId(complaint.getResident().getrId());
            complaintDTO.setComplainerName(complaint.getComplainerName());
            complaintDTO.setDate(complaint.getDate());
            complaintDTO.setDescription(complaint.getDescription());
            complaintDTO.setSolutionMsg(complaint.getSolutionMsg());

            complaintDTOs.add(complaintDTO);
        }
        }

        return complaintDTOs;
    }
    
    
    @Override
    public void addComplaintReply(String key,Long cid, ComplaintReplyDTO complaintReplyDTO) throws LoginException {
        CurrentUserSession currentUserSession = currSession.findByPrivateKey(key);
        if (currentUserSession == null) {
            throw new LoginException("Login required");
        }
        
        //Long cid = complaintReplyDTO.getCid();

        // Check if a reply already exists for the given complaint
        if (complaintReplyRepository.existsByComplaint_Cid(cid)) {
            throw new IllegalStateException("A reply already exists for this complaint");
        }

        ComplaintReply complaintReply = new ComplaintReply();

        // Fetch Complaint and Resident using provided ids
        Complaint complaint = complaintRepository.findById(cid)
                .orElseThrow(() -> new NotFoundException("Complaint not found"));

        Resident resident = complaint.getResident();

        complaintReply.setComplaint(complaint);
        complaintReply.setResident(resident);
        complaintReply.setDate(LocalDate.now());
        complaintReply.setResponse(complaintReplyDTO.getResponse());

        // Fetch the associated Complaint and set additional fields
        complaintReply.setComplaintDescription(complaint.getDescription());
        complaintReply.setSolutionMsg(complaint.getSolutionMsg());
        complaintReply.setComplaintDate(complaint.getDate());
        complaintReply.setComplainerName(complaint.getComplainerName());

        complaintReplyRepository.save(complaintReply);
    }
    
    
    @Override
    public List<ComplaintReplyDTO> getAllComplaintReplies(String key) throws LoginException {
        CurrentUserSession currentUserSession = currSession.findByPrivateKey(key);
        if (currentUserSession == null) {
            throw new LoginException("Login required");
        }

        List<ComplaintReply> complaintReplies = complaintReplyRepository.findAll();

        List<ComplaintReplyDTO> complaintReplyDTOs = new ArrayList<>();
        for (ComplaintReply complaintReply : complaintReplies) {
            ComplaintReplyDTO complaintReplyDTO = new ComplaintReplyDTO();
            // Map the attributes from ComplaintReply to ComplaintReplyDTO
            complaintReplyDTO.setReplyId(complaintReply.getReplyId());
            complaintReplyDTO.setCid(complaintReply.getComplaint().getCid());
            complaintReplyDTO.setrId(complaintReply.getResident().getrId());
            complaintReplyDTO.setComplainerName(complaintReply.getComplainerName());
            complaintReplyDTO.setDate(complaintReply.getDate()); 
            complaintReplyDTO.setComplaintDate(complaintReply.getComplaintDate());
            complaintReplyDTO.setComplaintDescription(complaintReply.getComplaintDescription());
            complaintReplyDTO.setSolutionMsg(complaintReply.getSolutionMsg());
            complaintReplyDTO.setResponse(complaintReply.getResponse());

            complaintReplyDTOs.add(complaintReplyDTO);
        }

        return complaintReplyDTOs;
    }
    
    @Override
    public ComplaintReplyDTO getComplaintReplyByReplyId(String key, Long replyId) throws LoginException, NotFoundException {
        CurrentUserSession currentUserSession = currSession.findByPrivateKey(key);
        if (currentUserSession == null) {
            throw new LoginException("Login required");
        }

        Optional<ComplaintReply> optionalComplaintReply = complaintReplyRepository.findById(replyId);
        if (optionalComplaintReply.isEmpty()) {
            throw new NotFoundException("Complaint reply not found");
        }

        ComplaintReply complaintReply = optionalComplaintReply.get();

        // Map ComplaintReply attributes to ComplaintReplyDTO
        ComplaintReplyDTO complaintReplyDTO = new ComplaintReplyDTO();
        complaintReplyDTO.setReplyId(complaintReply.getReplyId());
        complaintReplyDTO.setCid(complaintReply.getComplaint().getCid());
        complaintReplyDTO.setrId(complaintReply.getResident().getrId());
        complaintReplyDTO.setComplaintDate(complaintReply.getComplaintDate());
        complaintReplyDTO.setDate(complaintReply.getDate());
        complaintReplyDTO.setComplainerName(complaintReply.getComplainerName());
        complaintReplyDTO.setComplaintDescription(complaintReply.getComplaintDescription());
        complaintReplyDTO.setSolutionMsg(complaintReply.getSolutionMsg());
        complaintReplyDTO.setResponse(complaintReply.getResponse());

        return complaintReplyDTO;
    }

    
    @Override
    public void updateComplaintReply(String key, Long replyId, ComplaintReplyDTO complaintReplyDTO) throws LoginException, NotFoundException {
        CurrentUserSession currentUserSession = currSession.findByPrivateKey(key);
        if (currentUserSession == null) {
            throw new LoginException("Login required");
        }

        ComplaintReply complaintReply = complaintReplyRepository.findById(replyId)
                .orElseThrow(() -> new NotFoundException("Complaint reply not found"));

        // Update fields based on complaintReplyDTO
        complaintReply.setResponse(complaintReplyDTO.getResponse());
        complaintReply.setDate(LocalDate.now());

        complaintReplyRepository.save(complaintReply);
    }

    @Override
    public void deleteComplaintReply(String key, Long replyId) throws LoginException, NotFoundException {
        CurrentUserSession currentUserSession = currSession.findByPrivateKey(key);
        if (currentUserSession == null) {
            throw new LoginException("Login required");
        }

        if (!complaintReplyRepository.existsById(replyId)) {
            throw new NotFoundException("Complaint reply not found");
        }

        complaintReplyRepository.deleteById(replyId);
    }



    // resident side
    
    @Override
    public List<ComplaintReplyDTO> getComplaintRepliesForResident(String key) throws LoginException {
        CurrentUserSession currentUserSession = currSession.findByPrivateKey(key);
        if (currentUserSession == null) {
            throw new LoginException("Login required");
        }

        Long rId = currentUserSession.getResident().getrId();
        List<ComplaintReply> complaintReplies = complaintReplyRepository.findByResident_rId(rId);

        List<ComplaintReplyDTO> complaintReplyDTOs = new ArrayList<>();
        for (ComplaintReply complaintReply : complaintReplies) {
            ComplaintReplyDTO complaintReplyDTO = new ComplaintReplyDTO();
            // Map the attributes from ComplaintReply to ComplaintReplyDTO
            complaintReplyDTO.setReplyId(complaintReply.getReplyId());
            complaintReplyDTO.setCid(complaintReply.getComplaint().getCid());
            complaintReplyDTO.setrId(complaintReply.getResident().getrId());
            complaintReplyDTO.setComplainerName(complaintReply.getComplainerName());
            complaintReplyDTO.setDate(complaintReply.getDate());
            complaintReplyDTO.setComplaintDate(complaintReply.getComplaintDate());
            complaintReplyDTO.setComplaintDescription(complaintReply.getComplaintDescription());
            complaintReplyDTO.setSolutionMsg(complaintReply.getSolutionMsg());        
            complaintReplyDTO.setResponse(complaintReply.getResponse());

            complaintReplyDTOs.add(complaintReplyDTO);
        }

        return complaintReplyDTOs;
    }
    
    
}
