package com.society.service;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.society.dto.ResidentDTO;
import com.society.entity.CurrentUserSession;
import com.society.entity.Resident;
import com.society.exception.LoginException;
import com.society.exception.NotFoundException;
import com.society.repository.CurrentUserSessionRepository;
import com.society.repository.ResidentRepository;

@Service
public class ResidentServiceImpl implements ResidentService {

    private final ResidentRepository residentRepository;
    private CurrentUserSessionRepository currSession;
    private final EmailServiceImpl emailService;


    public ResidentServiceImpl(ResidentRepository residentRepository,CurrentUserSessionRepository currSession,EmailServiceImpl emailService) {
        this.residentRepository = residentRepository;
        this.currSession = currSession;
        this.emailService = emailService;
    }

    @Override
    public void registerResident(ResidentDTO residentDTO) {
    	
    	int currentYear = LocalDate.now().getYear();
        int birthYear = residentDTO.getBirthYear();

        if (currentYear - birthYear < 18) {
            throw new IllegalArgumentException("Age should be above 18");
        }
     	
    	
    	//String birthYear = residentDTO.getBirthYear().toString();
        String firstNameInitials = residentDTO.getfName().substring(0, Math.min(residentDTO.getfName().length(), 4));
        String defaultPassword = firstNameInitials + birthYear;
        residentDTO.setPassword(defaultPassword);
        
        Resident resident = new Resident();
        resident.setEmail(residentDTO.getEmail());
        resident.setfName(residentDTO.getfName());
        resident.setmInit(residentDTO.getmInit());
        resident.setlName(residentDTO.getlName());
        resident.setWingNo(residentDTO.getWingNo());
        resident.setFlatNo(residentDTO.getFlatNo());
        resident.setFloorNo(residentDTO.getFloorNo());
        resident.setMemberCount(residentDTO.getMemberCount());
        resident.setTwoWheelerCount(residentDTO.getTwoWheelerCount());
        resident.setFourWheelerCount(residentDTO.getFourWheelerCount());  
        resident.setBirthYear(residentDTO.getBirthYear());
        resident.setPassword(residentDTO.getPassword());

        residentRepository.save(resident);
    }
    
    

    @Override
    public void updateResident(String key,Long rId, ResidentDTO residentDTO) throws LoginException {
    	
    	CurrentUserSession currSess = currSession.findByPrivateKey(key);
		if (currSess == null)
			throw new LoginException("Login required");
		
        Resident resident = residentRepository.findById(rId)
                .orElseThrow(() -> new NotFoundException("Resident not found"));
        
        int currentYear = LocalDate.now().getYear();
        int birthYear = residentDTO.getBirthYear();

        if (currentYear - birthYear < 18) {
            throw new IllegalArgumentException("Age should be above 18");
        }

        resident.setEmail(residentDTO.getEmail());
        resident.setfName(residentDTO.getfName());
        resident.setmInit(residentDTO.getmInit());
        resident.setlName(residentDTO.getlName());
        resident.setWingNo(residentDTO.getWingNo());
        resident.setFlatNo(residentDTO.getFlatNo());
        resident.setFloorNo(residentDTO.getFloorNo());
        resident.setMemberCount(residentDTO.getMemberCount());
        resident.setBirthYear(residentDTO.getBirthYear());
        resident.setTwoWheelerCount(residentDTO.getTwoWheelerCount());
        resident.setFourWheelerCount(residentDTO.getFourWheelerCount());
        resident.setPassword(residentDTO.getPassword());

        residentRepository.save(resident);
    }
    
    
    @Override
    public void sendForgotPasswordEmail(String email) throws NotFoundException {
    	
    	Resident resident = residentRepository.findByEmail(email);
        
        if (resident == null) {
            throw new NotFoundException("User with the provided email not found.");
        }

        // For debugging purposes, log the retrieved email
        System.out.println("Retrieved Email: " + resident.getEmail());

        // Send password in the email
        String passwordMessage = generatePasswordMessage(resident);
        emailService.sendEmail(resident.getEmail(), "Password Recovery", passwordMessage);
    }

    private String generatePasswordMessage(Resident resident) {
        return "Dear " + resident.getfName() +" " + resident.getlName() + "," + "\n" 
        + "\n"+"We hope this email finds you well. We wanted to inform you about your account credentials.\n"
        +"\n"+"Your password is: " + resident.getPassword()+ "\n" 
        + "\n"+"Please remember to keep this password secure and do not share it with anyone. "
        + "If you ever suspect any unauthorized activity on your account or have any concerns, "
        + "please don't hesitate to contact our support team at jasmine.society@gmail.com.\n"
        + "\n"+"Thank you for being a valued member of our community.\n"
        + "\n"+"Best regards"
        ;
    }
    
    
    /*@Override
    public List<ResidentDTO> getAllResidents(String key) throws LoginException{
    	
    	CurrentUserSession currSess = currSession.findByPrivateKey(key);
		if (currSess == null)
			throw new LoginException("Login required");
		
        List<Resident> residents = residentRepository.findAll();
        List<ResidentDTO> residentDTOs = new ArrayList<>();

        for (Resident resident : residents) {
            ResidentDTO residentDTO = new ResidentDTO();
            residentDTO.setrId(resident.getrId());
            residentDTO.setEmail(resident.getEmail());
            residentDTO.setfName(resident.getfName());
            residentDTO.setmInit(resident.getmInit());
            residentDTO.setlName(resident.getlName());
            residentDTO.setWingNo(resident.getWingNo());
            residentDTO.setFlatNo(resident.getFlatNo());
            residentDTO.setFloorNo(resident.getFloorNo());
            residentDTO.setMemberCount(resident.getMemberCount());

            residentDTOs.add(residentDTO);
        }

        return residentDTOs;
    }*/
    
    @Override
    public List<ResidentDTO> getAllResidents(String key) throws LoginException{
        CurrentUserSession currSess = currSession.findByPrivateKey(key);
        if (currSess == null)
            throw new LoginException("Login required");

        Resident loggedInResident = currSess.getResident(); // Assuming this method returns the logged-in resident

        List<Resident> residents = residentRepository.findAll();
        List<ResidentDTO> residentDTOs = new ArrayList<>();

        for (Resident resident : residents) {
            // Skip the logged-in resident
            if (resident.getrId().equals(loggedInResident.getrId())) {
                continue;
            }

            ResidentDTO residentDTO = new ResidentDTO();
            residentDTO.setrId(resident.getrId());
            residentDTO.setEmail(resident.getEmail());
            residentDTO.setfName(resident.getfName());
            residentDTO.setmInit(resident.getmInit());
            residentDTO.setlName(resident.getlName());
            residentDTO.setWingNo(resident.getWingNo());
            residentDTO.setFlatNo(resident.getFlatNo());
            residentDTO.setFloorNo(resident.getFloorNo());
            residentDTO.setMemberCount(resident.getMemberCount());

            residentDTOs.add(residentDTO);
        }

        return residentDTOs;
    }

    
    @Override
    public Resident getResidentById(Long rId) {
        return residentRepository.findById(rId)
                .orElseThrow(() -> new NotFoundException("Resident not found"));
    }
    
    //newly added 16th august
    @Override
    public ResidentDTO getMyProfile(String key) throws LoginException {
    	CurrentUserSession currSess = currSession.findByPrivateKey(key);
		if (currSess == null)
			throw new LoginException("Login required");

        Long residentId = currSess.getResident().getrId();

        // Fetch resident details using residentId directly from the repository
        Resident resident = residentRepository.findById(residentId)
                .orElseThrow(() -> new NotFoundException("Resident not found"));

        // Convert resident entity to DTO
        ResidentDTO residentDTO = new ResidentDTO();
        residentDTO.setrId(resident.getrId());
        residentDTO.setEmail(resident.getEmail());
        residentDTO.setfName(resident.getfName());
        residentDTO.setmInit(resident.getmInit());
        residentDTO.setlName(resident.getlName());
        residentDTO.setPassword(resident.getPassword());
        residentDTO.setWingNo(resident.getWingNo());
        residentDTO.setFlatNo(resident.getFlatNo());
        residentDTO.setFloorNo(resident.getFloorNo());
        residentDTO.setMemberCount(resident.getMemberCount());
        residentDTO.setTwoWheelerCount(resident.getTwoWheelerCount());
        residentDTO.setFourWheelerCount(resident.getFourWheelerCount());
        residentDTO.setBirthYear(resident.getBirthYear());

        return residentDTO;
    }

}

