package com.example.closeup.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto {
    private Long id;
    private String impUid;
    private String merchantUid;
    private String payMethod;
    private String paidAmount;
    private String userId;
    private Long expertServiceId;
}
