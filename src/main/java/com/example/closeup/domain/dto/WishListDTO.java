package com.example.closeup.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WishListDTO {
    private Integer wishNo;
    private UserDto user;  // 즐겨찾기를
    private ExpertDto expert;

}
