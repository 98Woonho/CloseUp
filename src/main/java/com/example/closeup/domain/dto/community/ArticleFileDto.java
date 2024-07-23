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
public class ArticleFileDto {
    private Integer id;
    private Integer articleId;
    private String name;
    private String type;
    private Integer size;
    private byte[] data;
    private LocalDateTime createdAt;

}
