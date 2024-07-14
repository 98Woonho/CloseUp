package com.example.closeup.domain.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ArticleDto {
    private Integer id;
    private String boardCode;
    private String userId;
    private String title;
    private String content;
    private Integer view;
    private LocalDateTime writtenAt;
    private LocalDateTime modifiedAt;
}
