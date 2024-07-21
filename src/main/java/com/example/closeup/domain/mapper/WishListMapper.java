package com.example.closeup.domain.mapper;

import com.example.closeup.domain.dto.ExpertDto;
import com.example.closeup.domain.dto.WishListDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WishListMapper {

//    즐겨찾기 전체 목록
    List<ExpertDto> getWishListByUserId(String userId);
//    즐겨찾기 등록(추가)
    void insertWishList(
            @Param("userId") String userId,
            @Param("expertId") String expertId
    );
//    즐겨찾기 제거(삭제)
    void deleteWishList(
            @Param("userId") String userId,
            @Param("expertId") String expertId
    );
}
