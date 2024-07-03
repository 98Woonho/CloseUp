package com.example.closeup.controller;

import com.example.closeup.dto.UserDto;
import com.example.closeup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    /***************** 회원가입 *******************/
    @GetMapping("/regist")
    public void getUserRegist(){
        System.out.println("get");
    }

    @PostMapping("/regist")
    public void postUserRegist(UserDto user){
        System.out.println("post_user_regist" + user);
        user.setPhone(user.getPhone().replace("-", ""));
        userService.createUser(user);
    }
}
