    package com.example.closeup.controller;

    import com.example.closeup.config.auth.PrincipalDetails;
    import com.example.closeup.domain.dto.ExpertDetailDto;
    import com.example.closeup.domain.dto.ExpertDto;

    import com.example.closeup.config.auth.PrincipalDetails;

    import com.example.closeup.domain.dto.OAuth2UserDto;
    import com.example.closeup.domain.dto.UserDto;
    import com.example.closeup.service.ExpertService;
    import jakarta.servlet.http.HttpSession;
    import lombok.Data;
    import lombok.extern.slf4j.Slf4j;
    import org.json.JSONObject;
    import org.springframework.http.*;
    import com.example.closeup.service.UserService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.annotation.AuthenticationPrincipal;
    import org.springframework.security.oauth2.core.user.OAuth2User;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.util.LinkedMultiValueMap;
    import org.springframework.util.MultiValueMap;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.client.RestTemplate;
    import org.springframework.web.servlet.mvc.support.RedirectAttributes;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestParam;

    import java.io.IOException;
    import java.security.Principal;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

@Slf4j
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired private ExpertService expertService;

    @GetMapping("confirmIdDup")
    public ResponseEntity<String> getConfirmIdDup(@RequestParam("id") String id) {
        UserDto userDto = userService.findUserById(id);

        if (userDto != null) {
            return new ResponseEntity<>("이미 존재하는 아이디입니다. 다른 아이디를 입력해 주세요.", HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>("사용 가능한 아이디입니다.", HttpStatus.OK);
    }

    @GetMapping("/login")
    public void getLogin(@RequestParam(value = "error", required = false) boolean error, Model model) {
        model.addAttribute("error", error);
    }


    //아이디 찾기
    @GetMapping("/findId")
    public String getFindID() {
        return "user/findId";
    }

    @PostMapping("/findId")
    public String postFindID(@RequestParam("name") String name,
                             @RequestParam("phone") String phone,
                             Model model) {
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
                return "user/findPwNewPw";
            } else {
                model.addAttribute("error", "이름 또는 아이디가 틀렸습니다.");
                return "user/findPW";
            }
        }

        @PostMapping("/findPwNewPw")
        public String resetPassword(@RequestParam("id") String id,
                                    @RequestParam("password") String newPassword,
                                    RedirectAttributes redirectAttributes)
        {
            boolean result = userService.resetPassword(id, newPassword);
            if (result) {
                redirectAttributes.addFlashAttribute("message", "비밀번호가 성공적으로 변경되었습니다.");
                return "redirect:/user/findPwSuccess";
            } else {
                redirectAttributes.addFlashAttribute("error", "비밀번호 변경에 실패했습니다.");
                return "redirect:/user/findPW";
            }
        }

        @GetMapping("/findPwSuccess")
        public void getFindPWSuccess() {

        }

        @GetMapping("/register")
        public String getRegister(
                @RequestParam(defaultValue = "false") String social,
                Model model,
                HttpSession session
        ) {
            if ("kakao".equals(social)) {
                PrincipalDetails principalDetails = (PrincipalDetails) session.getAttribute("oAuth2UserInfo");
                if (principalDetails != null) {
                    model.addAttribute("socialUserInfo", principalDetails.getUserDto());
                    model.addAttribute("socialProvider", "kakao");
                    session.removeAttribute("oAuth2UserInfo"); //
                }
            }
            return "user/register";
        }

        @PostMapping("/register")
        public ResponseEntity<String> postRegister(@ModelAttribute UserDto userDto, Authentication authentication) {

            userService.register(userDto);

            return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다.");
        }

    // portOne 엑세스 토큰 받기
    @GetMapping("token")
    public String AccessToken(){
        String url = "https://api.iamport.kr/users/getToken";
        // HEADER
        HttpHeaders headers = new HttpHeaders();

            // PARAM
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("imp_key", "7582034642764268");
            params.add("imp_secret", "JxMwheK2PKBrxFxOifDLwwZvdyzjwDERKj4TzStgSZ06Wmg3oQp7h3WjK3nOfdjXsSXF0ZNgCbBWyPrV");

            // Entity
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

            // REQUEST
            RestTemplate rt = new RestTemplate();
            ResponseEntity<PortOneTokenResponse> response = rt.exchange(url, HttpMethod.POST, entity, PortOneTokenResponse.class);

            //RESPONSE
            return response.getBody().getResponse().getAccess_token();
        }

        //access토큰 객체
        @Data
        private static class TokenResponse{
            public String access_token;
            public int now;
            public int expired_at;
        }

        @Data
        private static class PortOneTokenResponse{
            public int code;
            public Object message;
            public TokenResponse response;
        }

    // 포트원 통합인증
    @GetMapping("cert/{imp_uid}")
    public @ResponseBody ResponseEntity<Map<String, String>> postCertification(@PathVariable("imp_uid") String impUid) {
        String accessToken = AccessToken(); // 엑세스 토큰 가져오기

        String url = "https://api.iamport.kr/certifications/"+impUid;

        // header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization","Bearer "+ accessToken);

        // params
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        // Entity
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        // request
        RestTemplate rt = new RestTemplate();

        // response
        ResponseEntity<PortOneAuthInfoResponse> response = rt.exchange(url, HttpMethod.GET,entity,PortOneAuthInfoResponse.class);

        // 통합인증 응답에서 name, phone 가져오기
        String name = response.getBody().getResponse().name;
        String phone = response.getBody().getResponse().phone;

        // name, phone으로 가입된 유저 조회
        UserDto user = userService.findUserByNameAndPhone(name, phone);

        Map <String, String> responseBody = new HashMap<>();

        // 이미 가입된 유저가 있을 때
        if (user != null) {
            responseBody.put("msg", "이미 해당 정보로 가입이 되어 있습니다. 로그인 화면으로 이동합니다.");
            return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);

            // 2024-07-16 ResponseEntity<String>으로 반환방법
//            return result == 1 ? ResponseEntity.ok("좋아요!") : ResponseEntity.status(HttpStatus.CONFLICT).body("알 수 없는 이유로 좋아요를 하지 못하였습니다. 잠시 후 다시 시도해 주세요.");
        }

        responseBody.put("name", name);
        responseBody.put("phone", phone);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    //인증 객체
    @Data
    private static class AuthInfoResponse{
        public int birth;
        public String birthday;
        public boolean certified;
        public int certified_at;
        public boolean foreigner;
        public Object foreigner_v2;
        public Object gender;
        public String imp_uid;
        public String merchant_uid;
        public String name;
        public String origin;
        public String pg_provider;
        public String pg_tid;
        public String phone;
        public Object unique_in_site;
        public String unique_key;
    }

    @Data
    private static class PortOneAuthInfoResponse{
        public int code;
        public Object message;
        public AuthInfoResponse response;
    }



    @GetMapping("/expertDetail/{nickname}")
    public String getExpertDetail(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable("nickname") String nickname,
            Model model
    ) {
        List<ExpertDetailDto> expertDetailDtoList = userService.getExpertDetailDtoList(nickname);
        ExpertDto expertDto = userService.findExpertByNickNameWithIsWished(principalDetails.getUsername(), nickname);

        model.addAttribute("expertDetailDtoList", expertDetailDtoList);
        model.addAttribute("expert", expertDto);
        return "user/expertDetail";
    }

    @GetMapping("")
    @ResponseBody
    public UserDto getUser(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return principalDetails.getUserDto();
    }

    // 전문가 닉네임 중복 확인
    @GetMapping("confirmNicknameDup")
    public ResponseEntity<String> getConfirmNicknameDup(@RequestParam("nickname") String nickname) {
        ExpertDto expertDto = userService.getExpertDto(nickname);

        if (expertDto != null) {
            return new ResponseEntity<>("이미 존재하는 닉네임니다. 다른 닉네임을 입력해 주세요.", HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>("사용 가능한 닉네임입니다.", HttpStatus.OK);
    }

    @PostMapping("/addExpertInfo")
    public ResponseEntity<String> addExpertInfo(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            ExpertDto expertDto
    ) {
        expertDto.setUserId(principalDetails.getUsername());
        expertDto.setProfileImg(principalDetails.getUserDto().getProfileImg());

            userService.insertExpertInfo(expertDto);
            expertService.insertExpertDetails(expertDto);
            userService.updateUserSuspendAndRoleById(principalDetails.getUsername());

            return new ResponseEntity<>("전문가 정보 등록에 성공하셨습니다.", HttpStatus.OK);
        }


        @GetMapping("/addExpertInfo")
        public String getAddExpertInfo(Model model) {
            return "user/addExpertInfo";
        }
    }
