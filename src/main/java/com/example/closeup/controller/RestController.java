package com.example.closeup.controller;

import com.example.closeup.config.auth.PrincipalDetails;
import com.example.closeup.domain.dto.ChatRoomDto;
import com.example.closeup.domain.dto.ExpertDto;
import com.example.closeup.domain.dto.UserDto;
import com.example.closeup.service.ExpertService;
import com.example.closeup.service.MyPageService;
import com.example.closeup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
        List<ExpertDto> expertInformation = expertService.selectExpertInformation();
        model.addAttribute("expertInfo", expertInformation);
        return expertInformation;
    }

    // 일반 유저 프로필 이미지 변경 요청
    @PutMapping("/imgChange")
    public void changeImg(
            // PrincipalDetails 안에 UserDto가 있기때문에 PrincipalDetails 객체 불러오고 거기서 username 가져옴
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody byte[] profileImg
    ) {
        principalDetails.getUserDto().setProfileImg(profileImg);
        userService.updateUserProfileImg(principalDetails.getUsername(), profileImg);
    }

    @PutMapping("/imgChangeExpert")
    public void changeImgExpert(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody byte[] profileImg
    ) {
        ExpertDto expertDto = expertService.selectExpertDtoByUserId(principalDetails.getUsername());
        expertDto.setProfileImg(profileImg);
        expertService.updateExpertProfileImg(expertDto.getUserId(), profileImg);
    }

    @PutMapping("/changeRole")
    public void changeRole(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody String role
    ) {
        principalDetails.getUserDto().setRole(role);
        userService.updateUserRoleByToggle(principalDetails.getUsername(), role);
    }

    @ResponseBody
    @GetMapping("/myPage/profileImage")
    public ResponseEntity<byte[]> getProfileImage(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestParam(value = "userId", required = false) String userId
    ) {
        UserDto userDto = userId != null ? userService.getUserDto(userId) : principalDetails.getUserDto();
        byte[] data = userDto.getProfileImg();
        return ResponseEntity.ok()
                .contentLength(data.length)
                .body(data);
    }

    @ResponseBody
    @GetMapping("/expert/profileImage")
    public ResponseEntity<byte[]> getExpertProfileImage(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestParam(value = "expertNickname", required = false) String expertNickname
    ) {
        ExpertDto expert = expertNickname != null ? expertService.getExpertDto(expertNickname) : expertService.selectExpertDtoByUserId(principalDetails.getUsername());
        byte[] data = expert.getProfileImg();
        return ResponseEntity.ok()
                .contentLength(data.length)
                .body(data);
    }
}
