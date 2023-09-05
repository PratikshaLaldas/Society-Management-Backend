package com.society.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.society.dto.SuggestionDTO;
import com.society.entity.CurrentUserSession;
import com.society.entity.Resident;
import com.society.entity.Suggestion;
import com.society.exception.LoginException;
import com.society.exception.NotFoundException;
import com.society.repository.CurrentUserSessionRepository;
import com.society.repository.ResidentRepository;
import com.society.repository.SuggestionReplyRepository;
import com.society.repository.SuggestionRepository;

@Service
public class SuggestionServiceImpl implements SuggestionService {

    @Autowired
    private SuggestionRepository suggestionRepository;
    
    @Autowired
    private SuggestionReplyRepository suggestionReplyRepository;

    @Autowired
    private CurrentUserSessionRepository currSession;

    @Autowired
    private ResidentRepository residentRepository;

    @Override
    public SuggestionDTO registerSuggestion(String key, SuggestionDTO suggestionDTO) throws LoginException {
        CurrentUserSession currSess = currSession.findByPrivateKey(key);
        if (currSess == null) {
            throw new LoginException("Login required");
        }

        Resident resident = currSess.getResident();
        if (resident == null) {
            throw new NotFoundException("Logged-in user is not a resident");
        }

        Long rId = currSess.getResident().getrId(); // Fetch rId from current_user_session

        Resident suggestionResident = residentRepository.findById(rId)
                .orElseThrow(() -> new NotFoundException("Resident not found"));


        Suggestion suggestion = new Suggestion();
        suggestion.setResident(suggestionResident);
        //newly added line
        suggestion.setSuggestionerName(suggestionResident.getfName() +" " + suggestionResident.getlName());
        suggestion.setDate(LocalDate.now());
        suggestion.setDescription(suggestionDTO.getDescription());

        suggestion = suggestionRepository.save(suggestion);

        suggestionDTO.setSid(suggestion.getSid());
        suggestionDTO.setrId(resident.getrId());
        suggestionDTO.setDate(suggestion.getDate());

        return suggestionDTO;
    }

    @Override
    public void updateSuggestion(String key, Long sid, SuggestionDTO suggestionDTO) throws LoginException {
        CurrentUserSession currSess = currSession.findByPrivateKey(key);
        if (currSess == null) {
            throw new LoginException("Login required");
        }

        Suggestion suggestion = suggestionRepository.findById(sid)
                .orElseThrow(() -> new NotFoundException("Suggestion not found"));

        //newly commented out line
        //suggestion.setSuggestionerName(suggestionDTO.getSuggestionerName());
        suggestion.setDescription(suggestionDTO.getDescription());

        suggestionRepository.save(suggestion);
    }

    @Override
    public void deleteSuggestion(String key, Long sid) throws LoginException {
        CurrentUserSession currSess = currSession.findByPrivateKey(key);
        if (currSess == null) {
            throw new LoginException("Login required");
        }

        if (!suggestionRepository.existsById(sid)) {
            throw new NotFoundException("Suggestion not found");
        }

        suggestionRepository.deleteById(sid);
    }

    @Override
    public List<SuggestionDTO> getAllSuggestions(String key) throws LoginException {
        CurrentUserSession currentUserSession = currSession.findByPrivateKey(key);
        if (currentUserSession == null) {
            throw new LoginException("Login required");
        }

        Long rId = currentUserSession.getResident().getrId();
        List<Suggestion> suggestions = suggestionRepository.findByResident_rId(rId);

        List<SuggestionDTO> suggestionDTOs = new ArrayList<>();
        for (Suggestion suggestion : suggestions) {
        	if (!suggestionReplyRepository.existsBySuggestion_sid(suggestion.getSid())) {
            SuggestionDTO suggestionDTO = new SuggestionDTO();
            // Map the attributes from Suggestion to SuggestionDTO
            suggestionDTO.setSid(suggestion.getSid());
            suggestionDTO.setrId(suggestion.getResident().getrId());
            suggestionDTO.setSuggestionerName(suggestion.getSuggestionerName());
            suggestionDTO.setDate(suggestion.getDate());
            suggestionDTO.setDescription(suggestion.getDescription());

            suggestionDTOs.add(suggestionDTO);
        }
        }

        return suggestionDTOs;
    }
    
    @Override
    public SuggestionDTO getSuggestionBySid(String key, Long sid) throws LoginException, NotFoundException {
        CurrentUserSession currentUserSession = currSession.findByPrivateKey(key);
        if (currentUserSession == null) {
            throw new LoginException("Login required");
        }

        Optional<Suggestion> optionalSuggestion = suggestionRepository.findById(sid);
        if (optionalSuggestion.isEmpty()) {
            throw new NotFoundException("Suggestion not found");
        }

        Suggestion suggestion = optionalSuggestion.get();

        // Map Suggestion attributes to SuggestionDTO
        SuggestionDTO suggestionDTO = new SuggestionDTO();
        suggestionDTO.setSid(suggestion.getSid());
        suggestionDTO.setrId(suggestion.getResident().getrId());
        suggestionDTO.setSuggestionerName(suggestion.getSuggestionerName());
        suggestionDTO.setDate(suggestion.getDate());
        suggestionDTO.setDescription(suggestion.getDescription());

        return suggestionDTO;
    }
    /* admin side suggestion */
    
   

}

