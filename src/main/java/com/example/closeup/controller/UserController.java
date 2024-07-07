package com.example.closeup.controller;

import com.example.closeup.dto.UserDto;
import com.example.closeup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
//아이디 찾기
    @GetMapping("/findID")
    public String getFindID() {
        return "user/findId";
    }

    @PostMapping("/findID")
    public String postFindID(@RequestParam("name") String name,
                             @RequestParam("phone") String phone,
                             Model model)
    {
        UserDto user = userService.findUserByNameAndPhone(name, phone);
        if (user != null) {
            model.addAttribute("foundId", user.getId());
            return "user/findIdSuccess";
        } else {
            model.addAttribute("error", "이름 또는 휴대폰번호가 틀렸습니다.");
            return "user/findId";
        }
    }
//비밀번호 찾기
    @GetMapping("/findPW")
    public String getFindPW() {
        return "user/findPW";
    }

    @PostMapping("/findPW")
    public String postFindPW(@RequestParam String name,
                             @RequestParam String id,
                             Model model)
    {
        UserDto user = userService.findUserByNameAndId(name, id);
        if (user != null) {
            model.addAttribute("userId", user.getId());
            return "user/findPW_newPW";
        } else {
            model.addAttribute("error", "이름 또는 아이디가 틀렸습니다.");
            return "user/findPW";
        }
    }

    @PostMapping("/findPW_newPW")
    public String resetPassword(@RequestParam String id,
                                @RequestParam String newPassword,
                                RedirectAttributes redirectAttributes)
    {
        boolean result = userService.resetPassword(id, newPassword);
        if (result) {
            redirectAttributes.addFlashAttribute("message", "비밀번호가 성공적으로 변경되었습니다.");
            return "redirect:/user/login";
        } else {
            redirectAttributes.addFlashAttribute("error", "비밀번호 변경에 실패했습니다.");
            return "redirect:/user/findPW";
        }
    }




    @GetMapping("/login")
    public void getUserLogin(){

    }

    /***************** 회원가입 *******************/
    @GetMapping("/regist")
    public void getUserRegist(){
        System.out.println("get");
    }

    @PostMapping("/regist")
    public String postUserRegist(
            UserDto user,
            RedirectAttributes redirectAttributes
    ){
        System.out.println("post_user_regist" + user);
        boolean result = userService.createUser(user);
        user.setPhone(user.getPhone().replace("-", ""));
        if(result){
            return "redirect:/user/login";
        }
        redirectAttributes.addFlashAttribute("certErrorMsg", "본인인증이 완료되지 않았습니다.");
        return "redirect:/user/register";
    }

}
