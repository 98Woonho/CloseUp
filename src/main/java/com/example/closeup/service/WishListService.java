package com.example.closeup.service;

import com.example.closeup.domain.dto.ExpertDto;
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
    public List<ExpertDto> getWishlist(String userId) {
        System.out.println(userId);
        return wishListMapper.getWishListByUserId(userId);
    }
    public void insertWishlist(String userId, String expertId) {
        wishListMapper.insertWishList(userId, expertId);
    }

    public void deleteWishlist(String userId, String expertId) {
        wishListMapper.deleteWishList(userId, expertId);
    }




}
