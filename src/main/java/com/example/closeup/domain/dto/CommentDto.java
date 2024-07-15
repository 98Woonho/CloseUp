package com.example.closeup.domain.dto;

import lombok.*;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Integer id;
    private Integer articleId;
    private String userId;
    private String commentId;
    private String content;
    private LocalDateTime writtenAt;
    private LocalDateTime modifiedAt;
}
