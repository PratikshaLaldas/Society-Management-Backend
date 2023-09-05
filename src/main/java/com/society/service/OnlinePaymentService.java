package com.society.service;

import com.society.entity.OnlinePayment;
import com.society.exception.LoginException;

public interface OnlinePaymentService {
    void makeOnlinePayment(String key, Long billNo, OnlinePayment paymentRequest) throws LoginException;
}