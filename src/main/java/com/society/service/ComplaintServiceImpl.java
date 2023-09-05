package com.society.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.society.dto.ComplaintDTO;
import com.society.entity.Complaint;
import com.society.entity.CurrentUserSession;
import com.society.entity.Resident;
import com.society.exception.LoginException;
import com.society.exception.NotFoundException;
import com.society.repository.ComplaintReplyRepository;
import com.society.repository.ComplaintRepository;
import com.society.repository.CurrentUserSessionRepository;
import com.society.repository.ResidentRepository;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private CurrentUserSessionRepository currSession;

    @Autowired
    private ResidentRepository residentRepository; // Add this repository
    
    @Autowired
    private ComplaintReplyRepository complaintReplyRepository;

    @Override
    public ComplaintDTO registerComplaint(String key, ComplaintDTO complaintDTO) throws LoginException{
    	CurrentUserSession currSess = currSession.findByPrivateKey(key);
		if (currSess == null)
			throw new LoginException("Login required");
		
    	
		Resident resident = currSess.getResident();
        if (resident == null) {
            throw new NotFoundException("Logged-in user is not a resident");
        }

        Long rId = currSess.getResident().getrId(); // Fetch rId from current_user_session

        Resident complaintResident = residentRepository.findById(rId)
                .orElseThrow(() -> new NotFoundException("Resident not found"));

        Complaint complaint = new Complaint();
        complaint.setResident(complaintResident);
        //newly added line
        complaint.setComplainerName(complaintResident.getfName()+" "+complaintResident.getlName());
        complaint.setDate(LocalDate.now());
        complaint.setDescription(complaintDTO.getDescription());
        complaint.setSolutionMsg(complaintDTO.getSolutionMsg());

        complaint = complaintRepository.save(complaint);
        
        complaintDTO.setCid(complaint.getCid());
        complaintDTO.setrId(resident.getrId());
        complaintDTO.setDate(complaint.getDate());
  

        return complaintDTO;
    }
    
    @Override
    public void updateComplaint(String key,Long cid, ComplaintDTO complaintDTO) throws LoginException{
    	
    	CurrentUserSession currSess = currSession.findByPrivateKey(key);
		if (currSess == null)
			throw new LoginException("Login required");
		
    	
        Complaint complaint = complaintRepository.findById(cid)
                .orElseThrow(() -> new NotFoundException("Complaint not found"));

        // Update complaint properties
        //newly commented out line
       // complaint.setComplainerName(complaintDTO.getComplainerName());
        complaint.setDescription(complaintDTO.getDescription());
        complaint.setSolutionMsg(complaintDTO.getSolutionMsg());

        complaintRepository.save(complaint);
    }

    @Override
    public void deleteComplaint(String key,Long cid) throws LoginException{
    	CurrentUserSession currSess = currSession.findByPrivateKey(key);
		if (currSess == null)
			throw new LoginException("Login required");
        
        if (!complaintRepository.existsById(cid)) {
            throw new NotFoundException("Complaint not found");
        }

        complaintRepository.deleteById(cid);
    }
    
    @Override
    public ComplaintDTO getComplaintByCid(String key, Long cid) throws LoginException, NotFoundException {
        CurrentUserSession currentUserSession = currSession.findByPrivateKey(key);
        if (currentUserSession == null) {
            throw new LoginException("Login required");
        }

        Optional<Complaint> optionalComplaint = complaintRepository.findById(cid);
        if (optionalComplaint.isEmpty()) {
            throw new NotFoundException("Complaint not found");
        }

        Complaint complaint = optionalComplaint.get();

        // Map Complaint attributes to ComplaintDTO
        ComplaintDTO complaintDTO = new ComplaintDTO();
        complaintDTO.setCid(complaint.getCid());
        complaintDTO.setrId(complaint.getResident().getrId());
        complaintDTO.setComplainerName(complaint.getComplainerName());
        complaintDTO.setDate(complaint.getDate());
        complaintDTO.setDescription(complaint.getDescription());
        complaintDTO.setSolutionMsg(complaint.getSolutionMsg());

        return complaintDTO;
    }
    
    /* can be used by admin side to view all complaints of all residents
    
    
    */
    
    @Override
    public List<ComplaintDTO> getAllComplaints(String key) throws LoginException {
        CurrentUserSession currentUserSession = currSession.findByPrivateKey(key);
        if (currentUserSession == null) {
            throw new LoginException("Login required");
        }

        Long rId = currentUserSession.getResident().getrId();
        List<Complaint> complaints = complaintRepository.findByResident_rId(rId);

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

    
    /* admin side complaint section */
    
    
}
