package com.example.closeup.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {
    private String id;
    private String boardCode;
    private String userId;
    private String title;
    private String content;
    private Integer view;
    private LocalDateTime writtenAt;
    private LocalDateTime modifiedAt;

}
