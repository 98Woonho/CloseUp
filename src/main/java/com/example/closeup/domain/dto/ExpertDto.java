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
    private Boolean isWished; // 유저가 이 전문가를 좋아요 하고 있는가?
    private byte[] profile_img;
}
