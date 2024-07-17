package com.example.closeup.domain.dto.community;

import lombok.*;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Integer id;
    private Integer articleId;
    private Integer likeCount;
    private String userId;
    private Integer commentId;
    private String content;
    private LocalDateTime writtenAt;
    private LocalDateTime modifiedAt;
}
