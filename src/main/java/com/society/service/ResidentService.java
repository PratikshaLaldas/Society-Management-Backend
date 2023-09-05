package com.society.service;

import java.util.List;

import com.society.dto.ResidentDTO;
import com.society.entity.Resident;
import com.society.exception.LoginException;
import com.society.exception.NotFoundException;

public interface ResidentService {
    void registerResident(ResidentDTO residentDTO);
    void updateResident(String key,Long rId, ResidentDTO residentDTO) throws LoginException;
    void sendForgotPasswordEmail(String email) throws NotFoundException;
	List<ResidentDTO> getAllResidents(String key) throws LoginException;
	Resident getResidentById(Long rId);
	ResidentDTO getMyProfile(String key) throws LoginException;
	
}
