package com.example.closeup.controller;

import com.example.closeup.config.auth.PrincipalDetails;
import com.example.closeup.domain.dto.ExpertDto;
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
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            Model model
    ){
        List<ExpertDto> wishlist = new ArrayList<>();
        if (!Objects.isNull(principalDetails)) {
            System.out.println("user is not null");
            wishlist = wishListService.getWishlist(principalDetails.getUserDto().getId());
        }

        for(ExpertDto expertDto : wishlist){
            System.out.println(expertDto.getProfileImgUrl());
        }
        model.addAttribute("expertList", wishlist);
        return "user/myPage/wishlist";
    }

    @ResponseBody
    @PostMapping("/wishlist/{expertId}")
    public ResponseEntity<Void> postWishlist(
            @AuthenticationPrincipal PrincipalDetails user,
            @PathVariable String expertId
    ) {
        if (Objects.isNull(user)) {
            log.error("로그인되지 않은 유저");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        wishListService.insertWishlist(user.getUsername(), expertId);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }


    @ResponseBody
    @DeleteMapping("/wishlist/{expertId}")
    public ResponseEntity<Void> deleteWishlist(
            @AuthenticationPrincipal PrincipalDetails user,
            @PathVariable String expertId
    ) {
//        log.info("expertId: " + expertId);
        if (Objects.isNull(user)) {
            log.error("로그인되지 않은 유저");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        wishListService.deleteWishlist(user.getUsername(), expertId);
        return ResponseEntity.ok().body(null);
    }
}