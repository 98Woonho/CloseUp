package com.example.closeup.domain.mapper;

import com.example.closeup.domain.dto.WishListDTO;

import java.util.List;

public interface WishListMapper {

//    즐겨찾기 전체 목록
    List<WishListDTO> getWishListByUserId(String userId);
//    즐겨찾기 등록(추가)
    void insertWishList(WishListDTO wishListDTO);
//    즐겨찾기 제거(삭제)
    void deleteWishList(List<WishListDTO> wishList);
}
