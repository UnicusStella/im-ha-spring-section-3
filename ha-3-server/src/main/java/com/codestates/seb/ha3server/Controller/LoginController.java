package com.codestates.seb.ha3server.Controller;

import com.codestates.seb.ha3server.Domain.LoginSignUp;
import com.codestates.seb.ha3server.Domain.LoginSignin;
import com.codestates.seb.ha3server.Entity.ServiceUser;
import com.codestates.seb.ha3server.Service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("https://localhost:3000")
public class LoginController {

    private final static Long ACCESS_TIME = 15L;
    private final static Long REFRESH_TIME = 1800L;

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService){
        this.loginService = loginService;
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<?> UserSignUp(@RequestBody(required = true)LoginSignUp loginSignUp, HttpServletResponse response){
        // 회원가입 요청을 위한 메소드입니다.
        // loginSignUp 객체에 내용을 사용하여 DB > service_user 테이블에 유저 정보를 저장하세요.
        // TODO :

        if (false){ // loginSignUp 객체 내에 모든 필드 값이 null이 아닌지 체크해야합니다.
            return ResponseEntity.badRequest().body("insufficient parameters supplied");
        }

        ServiceUser user = null; // 유저 정보를 저장해야합니다.
        if(user == null) {
            return ResponseEntity.badRequest().body("email exists");
        }

        Cookie cookie = null; // jwt 토큰을 생성하여 쿠키를 통해 클라이언트에 전달해야 합니다. (cookie key -> "jwt")

        return ResponseEntity.ok().body(null);  // 유저 정보가 저장 되고 토큰 값이 쿠키를 통해 정상적으로 전달이 되면 아래 JSON 내용이 body에 전달되어야 합니다.
                                                // { "message" : "ok"}

    }

    @PostMapping(value = "/signin")
    public ResponseEntity<?> UserSignIn(@RequestBody(required = true)LoginSignin loginSignin, HttpServletResponse response){
        try{
            // DB에 저장 된 유저정보를 확인하여 토큰을 발행하는 메소드입니다.
            // loginSignin를 통해 전달 된 정보를 DB > service_user 테이블에 유저 정보와 비교 후 유효한 유저일 경우 토큰을 생성하여 쿠키를 통해 클라이언트에 전달해야합니다. (cookie key -> "jwt")
            // TODO :
            ServiceUser user = loginService.FindUserData(loginSignin); // 유저 정보를 체크해야 합니다.
            if(user == null){
                return ResponseEntity.badRequest().body("invalid user");
            }

//            Cookie cookie = new Cookie("refreshToken",loginService.CreateJWTToken(user, REFRESH_TIME)); // 토큰을 생성하여 쿠키를 통해 클라이언트에 전달 되어야 합니다.


            return ResponseEntity.ok().body(null); // { "message" : "ok"} 해당 JSON 데이터가 body에 전달되어야 합니다.

        }catch (Exception e){
            return ResponseEntity.badRequest().body("error : " + e);
        }
    }

    @PostMapping(value = "/signout")
    public ResponseEntity<?> UserSignOut(HttpServletResponse response){
        // 유저 로그아웃 기능을 수행하는 메소드입니다.
        // 해당 요청이 들어 왔을 시, 클라이언트에 jwt 키 값을 가진 쿠키가 제거 되어야합니다.
        // TODO :
        Cookie cookie = null;

        return ResponseEntity.ok().body("Logged out successfully");
    }

    @GetMapping(value = "/auth")
    public ResponseEntity<?> UserAuth(HttpServletRequest request){
        // 로그인 중인지 확인하는 메소드입니다.
        // 쿠키에 저장 된 토큰을 확인 하여 유저 데이터를 전달애햐합니다.
        // TODO :
        Cookie[] cookies = null;
        String cookiesResult = "";
        try{
            // 쿠키에 키 값이 "jwt"인 쿠키에 값을 찾아냅니다.


        }catch (NullPointerException e){
            return ResponseEntity.badRequest().body(null);// { "data" : null, "message" : "not authorized"} 해당 JSON 데이터가 body에 전달되어야 합니다.
        }

        Map<String, String> checkResult = null; // 토큰 유효성을 체크해야합니다.

        if(checkResult.get("email") != null){

            ServiceUser userResult = null; // email을 기준으로 유저 정보를 찾아야 합니다.

            return ResponseEntity.ok().body(null);
                // { "data" : { "userInfo" : { "email" : "유저 이메일", "username" : "유저 이름", "mobile" : "유저 모바일 번호" } }, "message" : "ok" }
                // 위와 같은 형태에 JSON 데이터가 body에 전달되어야 합니다.

        }else{
            return ResponseEntity.badRequest().body(null);
                // { "data" : null, "message" : "에러 내용" }
                // 위와 같은 형태에 JSON 데이터가 body에 전달되어야 합니다.

        }
    }

}
