package com.example.closeup.service;

import com.example.closeup.domain.dto.UserDto;
import com.example.closeup.domain.dto.WishListDTO;
import com.example.closeup.domain.mapper.WishListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// 즐겨찾기
@Service
public class WishListService {
    @Autowired
    private WishListMapper wishListMapper;
// user의 즐겨찾기 내역 불러오기
    public List<WishListDTO> getWishlist(UserDto user) {
        System.out.println(user);
        return wishListMapper.getWishListByUserId(user.getId());
    }
    public void insertWishlist(UserDto user, WishListDTO wishListDTO) {

    }

    public void deleteWishlist() {}




}
