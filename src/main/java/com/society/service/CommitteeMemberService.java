package com.society.service;

import java.util.List;

import com.society.dto.CommitteeMemberDTO;
import com.society.dto.ResidentDTO;
import com.society.exception.LoginException;

public interface CommitteeMemberService {
    void registerCommitteeMember(CommitteeMemberDTO committeeMemberDTO);
    void updateCommitteeMember(String key, Long aId, CommitteeMemberDTO committeeMemberDTO) throws LoginException;
	CommitteeMemberDTO getMyProfile(String key) throws LoginException;
	List<ResidentDTO> getAllResidents(String key) throws LoginException;
}
