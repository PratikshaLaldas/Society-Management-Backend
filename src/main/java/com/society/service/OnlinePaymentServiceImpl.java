package com.society.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.society.entity.Account;
import com.society.entity.CurrentUserSession;
import com.society.entity.OnlinePayment;
import com.society.entity.Resident;
import com.society.exception.LoginException;
import com.society.repository.AccountRepository;
import com.society.repository.CurrentUserSessionRepository;
import com.society.repository.OnlinePaymentRepository;
import com.society.repository.ResidentRepository;

@Service
public class OnlinePaymentServiceImpl implements OnlinePaymentService {

    private final OnlinePaymentRepository onlinePaymentRepository;
    private final AccountRepository accountRepository;
    private final ResidentRepository residentRepository;
    private final CurrentUserSessionRepository currentUserSessionRepository;

    @Autowired
    public OnlinePaymentServiceImpl(OnlinePaymentRepository onlinePaymentRepository,AccountRepository accountRepository,ResidentRepository residentRepository, CurrentUserSessionRepository currentUserSessionRepository) {
        this.onlinePaymentRepository = onlinePaymentRepository;
        this.accountRepository = accountRepository;
        this.residentRepository = residentRepository;
        this.currentUserSessionRepository = currentUserSessionRepository;
    }

    @Override

    public void makeOnlinePayment(String key, Long billNo, OnlinePayment paymentRequest) throws LoginException {
        CurrentUserSession currentUserSession = currentUserSessionRepository.findByPrivateKey(key);
        if (currentUserSession == null) {
            throw new LoginException("Login required");
        }

        Account account = accountRepository.findById(billNo).orElse(null);
        if (account != null && "unpaid".equalsIgnoreCase(account.getStatus())) {
            Resident resident = account.getResident();

            OnlinePayment onlinePayment = new OnlinePayment();
            onlinePayment.setResident(resident);
            onlinePayment.setAccount(account);
            onlinePayment.setStreet(paymentRequest.getStreet());
            onlinePayment.setCity(paymentRequest.getCity());
            onlinePayment.setCountry(paymentRequest.getCountry());
            onlinePayment.setZipcode(paymentRequest.getZipcode());
            onlinePayment.setAmount(paymentRequest.getAmount());

            onlinePaymentRepository.save(onlinePayment);

            // Validate amount
            if (account.getAmount().equals(paymentRequest.getAmount())) {
                // Update account status to "paid"
                account.setStatus("paid");
                accountRepository.save(account);
            }
            else {
                throw new RuntimeException("Amount is incorrect. Please enter correct amount");
            }
        }
    }
}








