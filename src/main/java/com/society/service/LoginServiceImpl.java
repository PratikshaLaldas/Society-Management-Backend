package com.society.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.society.dto.LoginDTO;
import com.society.dto.ResidentDTO;
import com.society.entity.CommitteeMember;
import com.society.entity.CurrentUserSession;
import com.society.entity.Resident;
import com.society.exception.LoginException;
import com.society.repository.CommitteeMemberRepository;
import com.society.repository.CurrentUserSessionRepository;
import com.society.repository.ResidentRepository;

import net.bytebuddy.utility.RandomString;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private ResidentRepository residentRepo;

	@Autowired
	private CurrentUserSessionRepository sessionRepo;
	
	@Autowired 
	private CommitteeMemberRepository committeememberRepo;
	
	
	@Override
	public String loginAccount(LoginDTO loginDTO) throws LoginException {

		if (loginDTO.getRole().equalsIgnoreCase("resident")) {

			Resident resident = residentRepo.findByEmail(loginDTO.getEmail());
			if (resident == null)
				throw new LoginException("Invalid email");

			if (resident.getPassword().equals(loginDTO.getPassword())) {

				CurrentUserSession cuurSession = sessionRepo.findByEmail(loginDTO.getEmail());

				if (cuurSession != null)
					throw new LoginException("User already logged-In!");

				CurrentUserSession currentUserSession = new CurrentUserSession();
				currentUserSession.setEmail(loginDTO.getEmail());
				currentUserSession.setLoginDateTime(LocalDateTime.now());
				currentUserSession.setRole("resident");
				currentUserSession.setResident(resident);
				String privateKey = RandomString.make(6);
				currentUserSession.setPrivateKey(privateKey);
				sessionRepo.save(currentUserSession);
				return privateKey;
			} else {
				throw new LoginException("Please Enter a valid password");
			}
		} 
		
		  else if (loginDTO.getRole().equalsIgnoreCase("committeemember")) { 
			  CommitteeMember committeemember = committeememberRepo.findByEmail(loginDTO.getEmail()); 
			  if (committeemember == null) 
				  throw new LoginException("Invalid email");
		  
		  if (committeemember.getPassword().equals(loginDTO.getPassword())) {
		  
		  CurrentUserSession cuurSession =
		  sessionRepo.findByEmail(loginDTO.getEmail());
		  
		  if (cuurSession != null) throw new LoginException("User already logged-In!");
		  
		  CurrentUserSession currentUserSession = new CurrentUserSession();
		  currentUserSession.setEmail(loginDTO.getEmail());
		  currentUserSession.setLoginDateTime(LocalDateTime.now());
		  currentUserSession.setRole("committeemember"); 
		  currentUserSession.setCommitteemember(committeemember);
		  String privateKey = RandomString.make(6); 
		  currentUserSession.setPrivateKey(privateKey);
		  
		  sessionRepo.save(currentUserSession);
		  return privateKey;
			} else {
				throw new LoginException("Please Enter a valid password");
			}
		  } 
				  return null;
}
		


	@Override
	public String logoutAccount(String role, String key) throws LoginException {
		
		 if (role == null || key == null) {
		        throw new LoginException("Role and key are required for logout");
		    }
		if (role.equalsIgnoreCase("resident")) {

			CurrentUserSession currSession = sessionRepo.findByPrivateKey(key);
			if (currSession == null)
				throw new LoginException("Invalid key");

			if (currSession.getRole().equalsIgnoreCase("resident")) {

				sessionRepo.delete(currSession);
				return "Logged Out!";

			} else
				throw new LoginException("Invalid role");

		} else if (role.equalsIgnoreCase("committeemember")) {

			CurrentUserSession currSession = sessionRepo.findByPrivateKey(key);
			if (currSession == null)
				throw new LoginException("Invalid key");

			if (currSession.getRole().equalsIgnoreCase("committeemember")) {

				sessionRepo.delete(currSession);
				return "Logged Out!";

			} else
				throw new LoginException("Invalid role");

		}
		
		else
			throw new LoginException("Invalid role");
	}
	
}


	