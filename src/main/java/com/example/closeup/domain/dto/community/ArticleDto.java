package com.example.closeup.domain.dto.community;


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
    private Integer id;  // 게시글 아이디
    private String boardCode;
    private String boardName;
    private BoardDto board;
    private String userId;
    private String title;
    private String content;
    private Integer view;
    private Integer likeCount;
    private LocalDateTime writtenAt;
    private LocalDateTime modifiedAt;

}
