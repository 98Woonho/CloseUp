package com.example.closeup.controller;

import com.example.closeup.domain.dto.UserDto;
import com.example.closeup.domain.dto.WishListDTO;
import com.example.closeup.service.WishListService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log4j2
@Controller
@RequestMapping("/myPage")
public class WishListController {
    @Autowired
    WishListService wishListService;

    @GetMapping("/wishlist")
    public String getWishlist(
            @AuthenticationPrincipal UserDto user,
            Model model
    ) {
        List<WishListDTO> wishlist = new ArrayList<>();
        if (!Objects.isNull(user)) {
            wishlist = wishListService.getWishlist(user);
        }
        log.info("wishlist: " + wishlist);
        model.addAttribute("wishlist", wishlist);
        return "user/myPage/wishlist";
    }

    @ResponseBody
    @PostMapping("/wishlist")
    public ResponseEntity<Void> postWishlist(
            @AuthenticationPrincipal UserDto user,
            WishListDTO wishlist
    ) {
        log.info("wishlist: " + wishlist);
        if (Objects.isNull(user)) {
            log.error("로그인되지 않은 유저");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        wishListService.insertWishlist(user, wishlist);
        // 장바구니 삽입 성공
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @ResponseBody
    @DeleteMapping("/wishlist")
    public ResponseEntity<Void> deleteWishlist() {
        wishListService.deleteWishlist();
        return ResponseEntity.ok().body(null);
    }
}