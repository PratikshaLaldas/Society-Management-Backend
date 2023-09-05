package com.society.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.society.dto.SuggestionDTO;
import com.society.dto.SuggestionReplyDTO;
import com.society.entity.CurrentUserSession;
import com.society.entity.Resident;
import com.society.entity.Suggestion;
import com.society.entity.SuggestionReply;
import com.society.exception.LoginException;
import com.society.exception.NotFoundException;
import com.society.repository.CurrentUserSessionRepository;
import com.society.repository.SuggestionReplyRepository;
import com.society.repository.SuggestionRepository;

@Service
public class SuggestionReplyServiceImpl implements SuggestionReplyService {

    @Autowired
    private SuggestionReplyRepository suggestionReplyRepository;

    @Autowired
    private SuggestionRepository suggestionRepository;

    @Autowired
    private CurrentUserSessionRepository currSession;

    //admin side
    
    @Override
    public List<SuggestionDTO> getAllSuggestionsOfAllMember(String key) throws LoginException {
        CurrentUserSession currSess = currSession.findByPrivateKey(key);
        if (currSess == null) {
            throw new LoginException("Login required");
        }

        List<Suggestion> suggestions = suggestionRepository.findAll();

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
    public void addSuggestionReply(String key,Long sid, SuggestionReplyDTO suggestionReplyDTO) throws LoginException {
        CurrentUserSession currentUserSession = currSession.findByPrivateKey(key);
        if (currentUserSession == null) {
            throw new LoginException("Login required");
        }

        // Check if a reply already exists for the given suggestion
        if (suggestionReplyRepository.existsBySuggestion_sid(sid)) {
            throw new IllegalStateException("A reply already exists for this suggestion");
        }

        SuggestionReply suggestionReply = new SuggestionReply();

        // Fetch Suggestion using provided id
        Suggestion suggestion = suggestionRepository.findById(sid)
                .orElseThrow(() -> new NotFoundException("Suggestion not found"));
        
        Resident resident = suggestion.getResident();

        suggestionReply.setSuggestion(suggestion);
        suggestionReply.setResident(resident);
        suggestionReply.setDate(LocalDate.now());
        suggestionReply.setResponse(suggestionReplyDTO.getResponse());

        // Fetch the associated Suggestion and set additional fields
        suggestionReply.setSuggestionDescription(suggestion.getDescription());
        suggestionReply.setSuggestionDate(suggestion.getDate());
        suggestionReply.setSuggestionerName(suggestion.getSuggestionerName());

        suggestionReplyRepository.save(suggestionReply);
    }

    @Override
    public List<SuggestionReplyDTO> getAllSuggestionReplies(String key) throws LoginException {
        CurrentUserSession currentUserSession = currSession.findByPrivateKey(key);
        if (currentUserSession == null) {
            throw new LoginException("Login required");
        }

        List<SuggestionReply> suggestionReplies = suggestionReplyRepository.findAll();

        List<SuggestionReplyDTO> suggestionReplyDTOs = new ArrayList<>();
        for (SuggestionReply suggestionReply : suggestionReplies) {
            SuggestionReplyDTO suggestionReplyDTO = new SuggestionReplyDTO();
            // Map the attributes from SuggestionReply to SuggestionReplyDTO
            suggestionReplyDTO.setReplyId(suggestionReply.getReplyId());
            suggestionReplyDTO.setSid(suggestionReply.getSuggestion().getSid());
            suggestionReplyDTO.setrId(suggestionReply.getResident().getrId());
            suggestionReplyDTO.setDate(suggestionReply.getDate());
            suggestionReplyDTO.setSuggestionDate(suggestionReply.getSuggestionDate());
            suggestionReplyDTO.setSuggestionDescription(suggestionReply.getSuggestionDescription());
            suggestionReplyDTO.setSuggestionerName(suggestionReply.getSuggestionerName());
            suggestionReplyDTO.setResponse(suggestionReply.getResponse());

            suggestionReplyDTOs.add(suggestionReplyDTO);
        }

        return suggestionReplyDTOs;
    }
    
    @Override
    public SuggestionReplyDTO getSuggestionReplyByReplyId(String key, Long replyId) throws LoginException, NotFoundException {
        CurrentUserSession currentUserSession = currSession.findByPrivateKey(key);
        if (currentUserSession == null) {
            throw new LoginException("Login required");
        }

        SuggestionReply suggestionReply = suggestionReplyRepository.findById(replyId)
                .orElseThrow(() -> new NotFoundException("Suggestion reply not found"));

        SuggestionReplyDTO suggestionReplyDTO = new SuggestionReplyDTO();
        // Map the attributes from SuggestionReply to SuggestionReplyDTO
        suggestionReplyDTO.setReplyId(suggestionReply.getReplyId());
        suggestionReplyDTO.setSid(suggestionReply.getSuggestion().getSid());
        suggestionReplyDTO.setrId(suggestionReply.getResident().getrId());
        suggestionReplyDTO.setDate(suggestionReply.getDate());
        suggestionReplyDTO.setSuggestionDate(suggestionReply.getSuggestionDate());
        suggestionReplyDTO.setSuggestionDescription(suggestionReply.getSuggestionDescription());
        suggestionReplyDTO.setSuggestionerName(suggestionReply.getSuggestionerName());
        suggestionReplyDTO.setResponse(suggestionReply.getResponse());

        return suggestionReplyDTO;
    }

    @Override
    public void updateSuggestionReply(String key, Long replyId, SuggestionReplyDTO suggestionReplyDTO) throws LoginException, NotFoundException {
        CurrentUserSession currentUserSession = currSession.findByPrivateKey(key);
        if (currentUserSession == null) {
            throw new LoginException("Login required");
        }

        SuggestionReply suggestionReply = suggestionReplyRepository.findById(replyId)
                .orElseThrow(() -> new NotFoundException("Suggestion reply not found"));

        // Update fields based on suggestionReplyDTO
        suggestionReply.setResponse(suggestionReplyDTO.getResponse());
        suggestionReply.setDate(LocalDate.now());

        suggestionReplyRepository.save(suggestionReply);
    }

    @Override
    public void deleteSuggestionReply(String key, Long replyId) throws LoginException, NotFoundException {
        CurrentUserSession currentUserSession = currSession.findByPrivateKey(key);
        if (currentUserSession == null) {
            throw new LoginException("Login required");
        }

        if (!suggestionReplyRepository.existsById(replyId)) {
            throw new NotFoundException("Suggestion reply not found");
        }

        suggestionReplyRepository.deleteById(replyId);
    }
    
    
    //resident side
    
    @Override
    public List<SuggestionReplyDTO> getSuggestionRepliesForResident(String key) throws LoginException {
        CurrentUserSession currentUserSession = currSession.findByPrivateKey(key);
        if (currentUserSession == null) {
            throw new LoginException("Login required");
        }

        Long rId = currentUserSession.getResident().getrId();
        List<SuggestionReply> suggestionReplies = suggestionReplyRepository.findByResident_rId(rId);

        List<SuggestionReplyDTO> suggestionReplyDTOs = new ArrayList<>();
        for (SuggestionReply suggestionReply : suggestionReplies) {
            SuggestionReplyDTO suggestionReplyDTO = new SuggestionReplyDTO();
            // Map the attributes from SuggestionReply to SuggestionReplyDTO
            suggestionReplyDTO.setReplyId(suggestionReply.getReplyId());
            suggestionReplyDTO.setSid(suggestionReply.getSuggestion().getSid());
            suggestionReplyDTO.setrId(suggestionReply.getResident().getrId());//newly added 23aug
            suggestionReplyDTO.setDate(suggestionReply.getDate());
            suggestionReplyDTO.setSuggestionDate(suggestionReply.getSuggestionDate());           
            suggestionReplyDTO.setSuggestionDescription(suggestionReply.getSuggestionDescription());
            suggestionReplyDTO.setSuggestionerName(suggestionReply.getSuggestionerName());
            suggestionReplyDTO.setResponse(suggestionReply.getResponse());

            suggestionReplyDTOs.add(suggestionReplyDTO);
        }

        return suggestionReplyDTOs;
    }
}
