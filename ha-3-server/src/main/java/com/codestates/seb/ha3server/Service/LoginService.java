package com.codestates.seb.ha3server.Service;

import com.codestates.seb.ha3server.Domain.LoginSignUp;
import com.codestates.seb.ha3server.Domain.LoginSignin;
import com.codestates.seb.ha3server.Entity.ServiceUser;
import com.codestates.seb.ha3server.Repository.LoginRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginService {

    private static long GET_ID = 0L;
    private final static String SIGN_KEY = "codestateskey";
    private final LoginRepository loginRepository;

    @Autowired
    public LoginService(LoginRepository loginRepository){
        this.loginRepository = loginRepository;
    }

    public ServiceUser FindUserData(LoginSignin loginSignin){
        List<ServiceUser> user = loginRepository.FindByEmail(loginSignin.getEmail());
        for (ServiceUser i : user){
            if (i.getPassword().equals(loginSignin.getPassword())){
                return i;
            }
        }
        return null;
    }

    public ServiceUser FindUserEmail(String email){
        return loginRepository.FindByEmail(email).get(0);
    }

    public ServiceUser CreateUserData(LoginSignUp loginSignUp){
        for(ServiceUser i : loginRepository.FindUserList()){
            if(i.getEmail().equals(loginSignUp.getEmail())){
                return null;
            }
        }
        GET_ID++;
        loginRepository.CreateServiceUser(loginSignUp, GET_ID);
        return loginRepository.FindById(GET_ID);
    }

    public String CreateJWTToken(ServiceUser user){
        // 매개변수 user를 통해 전달 되는 데이터를 사용하여 토큰을 생성 후 값을 리턴합니다.
        // 토큰에는 "email", "username"이 담겨야합니다.
        // 토큰에 유효시간은 24시간입니다.
        // SIGN_KEY를 사용해야합니다.
        //TODO :
        return null;
    }

    public Map<String, String> CheckJWTToken(String key){
        // 매개변수 key를 통해 전달 되는 토큰 값에 유효성을 체크하여 결과를 리턴합니다.
        try{
            //TODO :
            Claims claims = null;

            String userEmail = null;
            return null;
            // { "email" : 유저 이메일, "message" : "ok" }
            // 위와 같은 형태에 데이터가 리턴 되어야 합니다.

        }catch(){ // 알맞은 에러 객체를 catch() 안에 선언해야합니다.

            return null;
            // { "email" : null, "message" : "토큰 시간이 만료 되었습니다." }
            // 위와 같은 형태에 데이터가 리턴 되어야 합니다.

        }catch(){ // 알맞은 에러 객체를 catch() 안에 선언해야합니다.

            return null;
            // { "email" : null, "message" : "토큰이 유효하지 않습니다." }
            // 위와 같은 형태에 데이터가 리턴 되어야 합니다.
        }
    }
}
