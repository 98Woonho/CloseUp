package com.example.closeup.domain.mapper;

import com.example.closeup.domain.dto.PaymentDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper {
    void insertPayment(PaymentDto paymentDto);
}
