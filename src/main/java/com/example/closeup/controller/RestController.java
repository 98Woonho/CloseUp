package com.example.closeup.controller;

import com.example.closeup.config.auth.PrincipalDetails;
import com.example.closeup.domain.dto.ExpertDto;
import com.example.closeup.service.ExpertService;
import com.example.closeup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired private ExpertService expertService;
    @Autowired private UserService userService;

    // 지도 데이터 요청
    @GetMapping("expert/mapData")
    public List<ExpertDto> getMapData(Model model) {
        List<ExpertDto> expertInformation = expertService.getExpertInformation();
        model.addAttribute("expertInfo", expertInformation);
        return expertInformation;
    }

    @PutMapping("/imgChange")
    public void changeImg(
            // PrincipalDetails 안에 UserDto가 있기때문에 PrincipalDetails 객체 불러오고 거기서 username 가져옴
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody byte[] profileImg
    ) {
        userService.updateUserProfileImg(principalDetails.getUsername(), profileImg);
    }
}
