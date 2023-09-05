package com.society.service;

import java.util.List;

import com.society.dto.SuggestionDTO;
import com.society.exception.LoginException;
import com.society.exception.NotFoundException;

public interface SuggestionService {
    SuggestionDTO registerSuggestion(String key, SuggestionDTO suggestionDTO) throws LoginException;
    void updateSuggestion(String key, Long sid, SuggestionDTO suggestionDTO) throws LoginException;
    void deleteSuggestion(String key, Long sid) throws LoginException;
    List<SuggestionDTO> getAllSuggestions(String key) throws LoginException;
	SuggestionDTO getSuggestionBySid(String key, Long sid) throws LoginException, NotFoundException;
}

