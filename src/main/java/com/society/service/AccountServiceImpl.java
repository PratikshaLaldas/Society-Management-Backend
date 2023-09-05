package com.society.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.society.dto.AccountDTO;
import com.society.entity.Account;
import com.society.entity.CommitteeMember;
import com.society.entity.CurrentUserSession;
import com.society.entity.Resident;
import com.society.exception.LoginException;
import com.society.exception.NotFoundException;
import com.society.repository.AccountRepository;
import com.society.repository.CurrentUserSessionRepository;

@Service
public class AccountServiceImpl implements AccountService {

	 private final AccountRepository accountRepository;
	 private final ResidentService residentService;
	 
	 @Autowired
	 private CurrentUserSessionRepository currentUserSessionRepository;

	 public AccountServiceImpl(AccountRepository accountRepository, ResidentService residentService) {
	        this.accountRepository = accountRepository;
	        this.residentService = residentService;
	 }
	 
	 //admin
	 
	
	 @Override
	 public void createBill(String key, Long rId, AccountDTO accountDTO) throws LoginException  {

	     CurrentUserSession currentUserSession = currentUserSessionRepository.findByPrivateKey(key);
	     if (currentUserSession == null) {
	         throw new LoginException("Login required");
	     }
	     
	     Resident resident = residentService.getResidentById(rId);

	     Integer twoWheelerCount = resident.getTwoWheelerCount();
	     Integer fourWheelerCount = resident.getFourWheelerCount();

	     Integer parkingCharge = calculateParkingCharges(twoWheelerCount, fourWheelerCount);

	     LocalDate currentDate = LocalDate.now();
	     LocalDate dueDate = currentDate.plusDays(15);

	     Integer securityCharge = 500; // Default security charge
	     Integer commonAreaUtilization = 750; // Default common area utilization charge

	    Long amount = calculateAmount(accountDTO.getMaintenanceFee(), parkingCharge, securityCharge, commonAreaUtilization);
 
	   
	     Account newBill = new Account();
	     newBill.setResident(resident);
	     newBill.setResidentFirstName(resident.getfName());
	     newBill.setResidentLastName(resident.getlName());
	     newBill.setMaintenanceFee(accountDTO.getMaintenanceFee());
	     newBill.setPeriod(accountDTO.getPeriod());
	     newBill.setDate(currentDate);
	     newBill.setDueDate(dueDate);
	     newBill.setSecurityCharge(securityCharge);
	     newBill.setCommonAreaUtilization(commonAreaUtilization);
	     newBill.setParkingCharge(parkingCharge);
	     newBill.setAmount(amount);
	     newBill.setStatus("unpaid");
	     
	     

	     accountRepository.save(newBill);
	 }



	    private Integer calculateParkingCharges(Integer twoWheelerCount, Integer fourWheelerCount) {
	        Integer parkingChargeTwoWheeler = 200;
	        Integer parkingChargeFourWheeler = 400;
	        
	        Integer totalParkingCharges = (twoWheelerCount * parkingChargeTwoWheeler) +
	                                      (fourWheelerCount * parkingChargeFourWheeler);
	        
	        return totalParkingCharges;
	    }


	    private Long calculateAmount(Integer maintenanceFee, Integer parkingCharge, Integer securityCharge, Integer commonAreaUtilization) {
	    	Long totalAmount = maintenanceFee.longValue() + parkingCharge.longValue() + securityCharge.longValue() + commonAreaUtilization.longValue();
	    	return totalAmount;
}
	    
	    //made changes in delete and update bill - 21 aug
	    @Override
	    public void updateBill(String key, Long billId, AccountDTO accountDTO) throws LoginException, NotFoundException {
	        CurrentUserSession currentUserSession = currentUserSessionRepository.findByPrivateKey(key);
	        if (currentUserSession == null) {
	            throw new LoginException("Login required");
	        }

	        Optional<Account> optionalBill = accountRepository.findById(billId);
	        if (!optionalBill.isPresent()) {
	            throw new NotFoundException("Bill not found");
	        }

	        Account bill = optionalBill.get();
	        CommitteeMember committeeMember = currentUserSession.getCommitteemember();

	        if (!committeeMember.getaId().equals(currentUserSession.getCommitteemember().getaId())) {
	            throw new NotFoundException("Bill not found");
	        }

	        if ("unpaid".equalsIgnoreCase(bill.getStatus())) {
	        // Calculate the new totalAmount using the updated maintenanceFee and other charges
	        Integer updatedMaintenanceFee = accountDTO.getMaintenanceFee();
	        Integer updatedParkingCharge = calculateParkingCharges(bill.getResident().getTwoWheelerCount(), bill.getResident().getFourWheelerCount());
	        Integer updatedSecurityCharge = 500; // Default security charge
	        Integer updatedCommonAreaUtilization = 750; // Default common area utilization charge
	        Long newTotalAmount = calculateAmount(updatedMaintenanceFee, updatedParkingCharge, updatedSecurityCharge, updatedCommonAreaUtilization);

	        // Update the bill attributes
	        bill.setMaintenanceFee(updatedMaintenanceFee);
	        bill.setPeriod(accountDTO.getPeriod());
	        bill.setParkingCharge(updatedParkingCharge);
	        bill.setSecurityCharge(updatedSecurityCharge);
	        bill.setCommonAreaUtilization(updatedCommonAreaUtilization);
	        bill.setAmount(newTotalAmount);
	        
	        //newly added
	        bill.setDate(LocalDate.now());

	        // Calculate and set the due date as 15 days from the current date
	        LocalDate dueDate = LocalDate.now().plusDays(15);
	        bill.setDueDate(dueDate);

	        Account savedBill = accountRepository.save(bill);
	    
	        if (savedBill != null) {
	            // Successfully updated
	        } else {
	            // Error occurred while saving
	            throw new RuntimeException("Error occurred while saving the updated bill");
	        }
	    } else {
	        throw new IllegalStateException("Cannot update a paid bill");
	  }
	        }


	    @Override
	    public void deleteBill(String key, Long billId) throws LoginException, NotFoundException {
	        CurrentUserSession currentUserSession = currentUserSessionRepository.findByPrivateKey(key);
	        if (currentUserSession == null) {
	            throw new LoginException("Login required");
	        }

	        Optional<Account> optionalBill = accountRepository.findById(billId);
	        if (!optionalBill.isPresent()) {
	            throw new NotFoundException("Bill not found");
	        }

	        Account bill = optionalBill.get();
	        CommitteeMember committeeMember = currentUserSession.getCommitteemember();

	        if (!committeeMember.getaId().equals(currentUserSession.getCommitteemember().getaId())) {
	            throw new NotFoundException("Bill not found");
	        }
            
	        if ("unpaid".equalsIgnoreCase(bill.getStatus())) {
	            accountRepository.delete(bill);
	        } else {
	            throw new IllegalStateException("Cannot delete a paid bill");
	        }
	    }

	    public AccountDTO getBillById(String key, Long billNo) throws LoginException, NotFoundException {
	        CurrentUserSession currentUserSession = currentUserSessionRepository.findByPrivateKey(key);
	        if (currentUserSession == null) {
	            throw new LoginException("Login required");
	        }

	        Account bill = accountRepository.findById(billNo)
	                .orElseThrow(() -> new NotFoundException("Bill not found"));

	        // Map Account attributes to AccountDTO
	        AccountDTO billDTO = new AccountDTO();
	        billDTO.setBillNo(bill.getBillNo());
	        billDTO.setrId(bill.getResident().getrId());
	        billDTO.setResidentFirstName(bill.getResident().getfName()); // Fetching fname from Resident
	        billDTO.setResidentLastName(bill.getResident().getlName());  // Fetching lname from Resident
	        billDTO.setMaintenanceFee(bill.getMaintenanceFee());
	        billDTO.setCommonAreaUtilization(bill.getCommonAreaUtilization());
	        billDTO.setParkingCharge(bill.getParkingCharge());
	        billDTO.setSecurityCharge(bill.getSecurityCharge());
	        billDTO.setPeriod(bill.getPeriod());
	        billDTO.setDate(bill.getDate());
	        billDTO.setDueDate(bill.getDueDate());
	        billDTO.setAmount(bill.getAmount());
	        billDTO.setStatus(bill.getStatus());

	        return billDTO;
	    }

	    @Override
	    public List<AccountDTO> getAllBills(String key) throws LoginException {
	        CurrentUserSession currentUserSession = currentUserSessionRepository.findByPrivateKey(key);
	        if (currentUserSession == null) {
	            throw new LoginException("Login required");
	        }

	        List<Account> allBills = accountRepository.findAll();

	        List<AccountDTO> billDTOs = new ArrayList<>();
	        for (Account bill : allBills) {
	            // Map the attributes from Account to AccountDTO
	            AccountDTO billDTO = new AccountDTO();
	            billDTO.setBillNo(bill.getBillNo());
	            billDTO.setrId(bill.getResident().getrId());
	            billDTO.setResidentFirstName(bill.getResident().getfName()); // Fetching fname from Resident
	            billDTO.setResidentLastName(bill.getResident().getlName());  // Fetching lname from Resident
	            billDTO.setMaintenanceFee(bill.getMaintenanceFee());
	            billDTO.setCommonAreaUtilization(bill.getCommonAreaUtilization());
	            billDTO.setParkingCharge(bill.getParkingCharge());
	            billDTO.setSecurityCharge(bill.getSecurityCharge());
	            billDTO.setPeriod(bill.getPeriod());
	            billDTO.setDate(bill.getDate());
	            billDTO.setDueDate(bill.getDueDate());
	            billDTO.setAmount(bill.getAmount());
	            billDTO.setStatus(bill.getStatus());
	           
	           

	            billDTOs.add(billDTO);
	        }

	     
	        return billDTOs;
	    }

	    
	    //resident side
	    //to view current bill whose status is unpaid means payment not yet done
	    @Override
	    public List<AccountDTO> getMyBills(String key) throws LoginException {
	        CurrentUserSession currentUserSession = currentUserSessionRepository.findByPrivateKey(key);
	        if (currentUserSession == null) {
	            throw new LoginException("Login required");
	        }

	        Long rId = currentUserSession.getResident().getrId();
	        List<Account> bills = accountRepository.findByResident_rId(rId);

	        List<AccountDTO> billDTOs = new ArrayList<>();
	        for (Account bill : bills) {
	        	if ("unpaid".equals(bill.getStatus())) { // Only include bills with "unpaid" status
	            AccountDTO billDTO = new AccountDTO();
	            // Map the attributes from Account to AccountDTO
	            billDTO.setBillNo(bill.getBillNo());
	            billDTO.setrId(bill.getResident().getrId());
	            billDTO.setResidentFirstName(bill.getResidentFirstName());
	            billDTO.setResidentLastName(bill.getResidentLastName());
	            billDTO.setMaintenanceFee(bill.getMaintenanceFee());
	            billDTO.setCommonAreaUtilization(bill.getCommonAreaUtilization());
	            billDTO.setParkingCharge(bill.getParkingCharge());
	            billDTO.setSecurityCharge(bill.getSecurityCharge());
	            billDTO.setPeriod(bill.getPeriod());
	            billDTO.setDate(bill.getDate());
	            billDTO.setDueDate(bill.getDueDate());
	            billDTO.setAmount(bill.getAmount());
	            billDTO.setStatus(bill.getStatus());
	          
	            billDTOs.add(billDTO);
	            
	        }
	        	
	      
	        }
	        return billDTOs;
	    }
	    
	    //to view previous bills whose status is paid
	    @Override
	    public List<AccountDTO> getMyPreviousBills(String key) throws LoginException {
	        CurrentUserSession currentUserSession = currentUserSessionRepository.findByPrivateKey(key);
	        if (currentUserSession == null) {
	            throw new LoginException("Login required");
	        }

	        Long rId = currentUserSession.getResident().getrId();
	        List<Account> bills = accountRepository.findByResident_rId(rId);

	        List<AccountDTO> billDTOs = new ArrayList<>();
	        for (Account bill : bills) {
	        	if ("paid".equals(bill.getStatus())) { // Only include bills with "unpaid" status
	            AccountDTO billDTO = new AccountDTO();
	            // Map the attributes from Account to AccountDTO
	            billDTO.setBillNo(bill.getBillNo());
	            billDTO.setrId(bill.getResident().getrId());
	            billDTO.setResidentFirstName(bill.getResidentFirstName());
	            billDTO.setResidentLastName(bill.getResidentLastName());
	            billDTO.setMaintenanceFee(bill.getMaintenanceFee());
	            billDTO.setCommonAreaUtilization(bill.getCommonAreaUtilization());
	            billDTO.setParkingCharge(bill.getParkingCharge());
	            billDTO.setSecurityCharge(bill.getSecurityCharge());
	            billDTO.setPeriod(bill.getPeriod());
	            billDTO.setDate(bill.getDate());
	            billDTO.setDueDate(bill.getDueDate());
	            billDTO.setAmount(bill.getAmount());
	            billDTO.setStatus(bill.getStatus());
	            
	      

	            billDTOs.add(billDTO);
	        }
	        }
	        return billDTOs;
	    }


		
	    
	
}
