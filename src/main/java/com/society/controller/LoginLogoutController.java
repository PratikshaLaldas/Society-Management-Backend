package com.society.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.society.dto.LoginDTO;
import com.society.exception.LoginException;
import com.society.service.LoginService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/app")
public class LoginLogoutController {

	@Autowired
	private LoginService loginService;

	@PostMapping("/login")
	public ResponseEntity<String> logIn(@RequestBody LoginDTO loginDTO) throws LoginException {
		String result = loginService.loginAccount(loginDTO);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout(@RequestBody Map<String, String> request) throws LoginException {
		String role = request.get("role");
		  String key = request.get("key");
		String result = loginService.logoutAccount(role, key);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

}
