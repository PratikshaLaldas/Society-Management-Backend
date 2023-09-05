package com.society.service;

import com.society.dto.LoginDTO;
import com.society.exception.LoginException;

public interface LoginService {

	public String loginAccount(LoginDTO loginDTO) throws LoginException;

	public String logoutAccount(String role, String key) throws LoginException;

	
}
