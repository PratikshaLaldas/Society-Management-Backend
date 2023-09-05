package com.society.service;

import java.util.List;

import com.society.dto.ComplaintDTO;
import com.society.dto.ComplaintReplyDTO;
import com.society.exception.LoginException;
import com.society.exception.NotFoundException;

public interface ComplaintReplyService {
    void addComplaintReply(String key,Long cid,ComplaintReplyDTO complaintReplyDTO) throws LoginException;
    List<ComplaintReplyDTO> getComplaintRepliesForResident(String key) throws LoginException;
	List<ComplaintReplyDTO> getAllComplaintReplies(String key) throws LoginException;
	ComplaintReplyDTO getComplaintReplyByReplyId(String key, Long replyId) throws LoginException, NotFoundException;	
	void updateComplaintReply(String key, Long replyId, ComplaintReplyDTO complaintReplyDTO) throws LoginException, NotFoundException;
	void deleteComplaintReply(String key, Long replyId) throws LoginException, NotFoundException;
	List<ComplaintDTO> getAllComplaintsOfAllMember(String key) throws LoginException;
	
}
