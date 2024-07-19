package com.example.closeup.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// 전문가 전문분야/기술/경력/학력/자격증 dto
public class ExpertDetailDto {
    private String expertNickname;
    private String category;
    private String information;
}
