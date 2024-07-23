package com.example.closeup.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpertDto {
    private String nickname;
    private String userId;
    private String introduction;
    private String zipcode;
    private String address;
    private String addressDetail;
    private String[] skills;
    private String[] expertises;
    private String[] careers;
    private String[] abilities;
    private byte[] profileImg;
}
