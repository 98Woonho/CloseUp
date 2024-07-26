package com.example.closeup.service;

import com.example.closeup.domain.dto.PaymentDto;
import com.example.closeup.domain.mapper.PaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    @Autowired
    private PaymentMapper paymentMapper;

    public void payment(PaymentDto paymentDto) {

        paymentMapper.insertPayment(paymentDto);
    }
}
