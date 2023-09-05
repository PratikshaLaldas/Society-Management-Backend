package com.society.service;

import java.util.List;

import com.society.dto.AccountDTO;
import com.society.exception.LoginException;
import com.society.exception.NotFoundException;

public interface AccountService {

	public void createBill(String key, Long rId, AccountDTO accountDTO) throws LoginException;
	
	public AccountDTO getBillById(String key, Long billNo) throws LoginException, NotFoundException;

	void updateBill(String key, Long billId, AccountDTO accountDTO) throws LoginException, NotFoundException;

	void deleteBill(String key, Long billId) throws LoginException, NotFoundException;

	List<AccountDTO> getAllBills(String key) throws LoginException;

	List<AccountDTO> getMyBills(String key) throws LoginException;

	List<AccountDTO> getMyPreviousBills(String key) throws LoginException;

	


}
