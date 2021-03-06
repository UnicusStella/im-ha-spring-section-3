package com.codestates.seb.ha3server;

import com.codestates.seb.ha3server.CodeStatesSubmit.Submit;
import com.codestates.seb.ha3server.Domain.LoginSignUp;
import com.codestates.seb.ha3server.Domain.LoginSignin;
import com.codestates.seb.ha3server.Entity.ServiceUser;
import com.codestates.seb.ha3server.Repository.LoginRepository;
import com.codestates.seb.ha3server.Service.LoginService;
import com.codestates.seb.ha3server.TestDomain.ResMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.persistence.EntityManager;
import javax.servlet.http.Cookie;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;

@AutoConfigureMockMvc
@SpringBootTest
public class HaTest {

    private static Submit submit = new Submit();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private LoginService loginService;

    @Autowired
    private LoginRepository loginRepository;

    @AfterAll
    static void after() throws Exception {
        submit.SubmitJson("im-ha-spring-section-3", 10);
        submit.ResultSubmit();
    }

    @BeforeEach
    public void beforEach() throws Exception{
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(MockMvcResultHandlers.print())
                .build();

        objectMapper = Jackson2ObjectMapperBuilder.json()
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .modules(new JavaTimeModule())
                .build();
    }

    @Test
    @DisplayName(value = "[Controller] ???????????? ?????? ???, ?????? ????????? DB??? ??????????????? ?????????.")
    void Controller_SignUp_Test()throws Exception{
        MvcResult result = null;
        String url = "/signup";
        ServiceUser saveUser = null;

        LoginSignUp userSignup = new LoginSignUp();
        userSignup.setEmail("java.kim@codestaes.com");
        userSignup.setPassword("121212");
        userSignup.setUsername("?????????");
        userSignup.setMobile("010-1234-1234");

        try{
            String content = objectMapper.writeValueAsString(userSignup);
            result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                            .content(content)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            ResMessage returnResult = objectMapper.readValue(result.getResponse().getContentAsString(), ResMessage.class);
            if(!returnResult.getMessage().equals("ok")){
                return;
            }

            saveUser = entityManager.createQuery("SELECT user FROM ServiceUser AS user WHERE user.username = '?????????'", ServiceUser.class)
                    .getResultList()
                    .get(0);

            submit.ResultSave(saveUser.getEmail().equals(userSignup.getEmail()));
        }catch (Exception e){
            System.out.println(e);
        }finally {
            Assertions.assertEquals(saveUser.getEmail(), userSignup.getEmail());
        }
    }

    @Test
    @DisplayName(value = "[Controller] ????????? ?????? ???, JWT ????????? ????????? ????????? ?????? ?????????????????? ???????????? ?????????.")
    void Controller_Signin_Test()throws Exception{
        MvcResult result = null;
        String url = "/signin";
        String userData = null;

        LoginSignUp userSignup = new LoginSignUp();
        userSignup.setEmail("coding.kim@codestaes.com");
        userSignup.setPassword("121212");
        userSignup.setUsername("?????????");
        userSignup.setMobile("010-1234-1234");

        LoginSignin userSignin = new LoginSignin();
        userSignin.setEmail("coding.kim@codestaes.com");
        userSignin.setPassword("121212");

        try{
            String signup_content = objectMapper.writeValueAsString(userSignup);
            String signin_content = objectMapper.writeValueAsString(userSignin);

            mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                            .content(signup_content)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                            .content(signin_content)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            ResMessage returnResult = objectMapper.readValue(result.getResponse().getContentAsString(), ResMessage.class);
            if(!returnResult.getMessage().equals("ok")){
                return;
            }

            Cookie cookie = result.getResponse().getCookie("jwt");
            Claims claims = Jwts.parser().setSigningKey("codestateskey")
                    .parseClaimsJws(cookie.getValue())
                    .getBody();

            userData = (String)claims.get("email");

            submit.ResultSave(userSignin.getEmail().equals(userData));
        }catch (Exception e){
            System.out.println(e);
        }finally {
            Assertions.assertEquals(userSignin.getEmail(),userData);
        }
    }

    @Test
    @DisplayName(value = "[Controller] ???????????? ?????? ???, ????????? ??????????????? ?????????.")
    void Controller_Signout_test()throws Exception{
        MvcResult result = null;
        String url = "/signout";
        Cookie cookie = null;

        LoginSignUp userSignup = new LoginSignUp();
        userSignup.setEmail("server.kim@codestaes.com");
        userSignup.setPassword("121212");
        userSignup.setUsername("?????????");
        userSignup.setMobile("010-1234-1234");

        LoginSignin userSignin = new LoginSignin();
        userSignin.setEmail("coding.kim@codestaes.com");
        userSignin.setPassword("121212");

        try{
            String signup_content = objectMapper.writeValueAsString(userSignup);
            String signin_content = objectMapper.writeValueAsString(userSignin);

            mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                            .content(signup_content)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            mockMvc.perform(MockMvcRequestBuilders.post("/signin")
                            .content(signin_content)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            cookie = result.getResponse().getCookie("jwt");
            submit.ResultSave(cookie.getValue() == null);

        }catch (Exception e){
            System.out.println(e);
        }finally {
            Assertions.assertEquals(cookie.getValue(),null);
        }
    }

    @Test
    @DisplayName(value = "[Controller] ????????? ????????? ?????? Token??? ???????????? ?????? ????????? ???????????? ?????????.")
    void Controller_Auth_Test() throws Exception{
        MvcResult result = null;
        String url = "/auth";
        String standard = "{\"data\":{\"userInfo\":{\"mobile\":\"010-1234-1234\",\"email\":\"token.kim@codestaes.com\",\"username\":\"?????????\"}},\"message\":\"ok\"}";
        Date now = new Date();

        LoginSignUp userSignup = new LoginSignUp();
        userSignup.setEmail("token.kim@codestaes.com");
        userSignup.setPassword("121212");
        userSignup.setUsername("?????????");
        userSignup.setMobile("010-1234-1234");

        try{
            String signup_content = objectMapper.writeValueAsString(userSignup);

            mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                            .content(signup_content)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            String token = Jwts.builder()
                    .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                    .setIssuer("fresh")
                    .setIssuedAt(now)
                    .setExpiration(new Date(now.getTime() + Duration.ofHours(24).toMillis()))
                    .claim("email", userSignup.getEmail())
                    .claim("username", userSignup.getUsername())
                    .signWith(SignatureAlgorithm.HS256, "codestateskey")
                    .compact();

            Cookie cookie = new Cookie("jwt", token);

            result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                            .cookie(cookie)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            submit.ResultSave(standard.equals(result.getResponse().getContentAsString()));
        }catch (Exception e){
            System.out.println(e);
        }finally {
            Assertions.assertEquals(standard,result.getResponse().getContentAsString());
        }

    }

    @Test
    @DisplayName(value = "[Service] ServiceUser ????????? ?????? ????????? Email??? Password??? ?????? ????????? ????????? ???????????? ?????????.")
    void Service_CreateToken_Test() throws Exception{

        ServiceUser serviceUser = new ServiceUser();
        serviceUser.setId(13);
        serviceUser.setEmail("hello Code States!");
        serviceUser.setPassword("12341234");
        serviceUser.setUsername("?????????");
        serviceUser.setMobile("010-4321-1234");
        serviceUser.setCreatedAt(new Date());
        serviceUser.setUpdatedAt(new Date());

        String token = loginService.CreateJWTToken(serviceUser);

        Claims claims = Jwts.parser().setSigningKey("codestateskey")
                        .parseClaimsJws(token)
                        .getBody();

        String tokenData = (String)claims.get("email");

        submit.ResultSave(serviceUser.getEmail().equals(tokenData));
        Assertions.assertEquals(serviceUser.getEmail(),tokenData);
    }

    @Test
    @DisplayName(value = "[Service] ????????? ?????? ?????? ????????? ???????????? ???????????? ????????? ???????????????.")
    void Service_CheckToken_Test() throws Exception{
        Date now = new Date();
        String token = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("fresh")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofHours(24).toMillis()))
                .claim("email", "myEmail")
                .claim("username", "1234")
                .signWith(SignatureAlgorithm.HS256, "codestateskey")
                .compact();

        Map<String, ?> returnData = loginService.CheckJWTToken(token);

        submit.ResultSave(returnData.get("email").equals("myEmail"));
        Assertions.assertEquals(returnData.get("email"),"myEmail");
    }

    @Test
    @DisplayName(value = "[Repository] DB ?????? ?????? ?????? ????????? ????????? ?????????.")
    void FindUserListTest() throws Exception{
        List<ServiceUser> result = loginRepository.FindUserList();
        List<ServiceUser> standard = entityManager.createQuery("SELECT user FROM ServiceUser AS user", ServiceUser.class).getResultList();

        submit.ResultSave(result.size() == standard.size());
        Assertions.assertEquals(result.size(),standard.size());
    }

    @Test
    @DisplayName(value = "[Repository] ????????? ?????? ?????? Id ?????? ???????????? ???????????? DB?????? ???????????????.")
    void FindByIdTest() throws Exception{
        ServiceUser result = loginRepository.FindById(13);
        ServiceUser standard = entityManager.find(ServiceUser.class, 13L);

        submit.ResultSave(result.getEmail().equals(standard.getEmail()));
        Assertions.assertEquals(standard.getCreatedAt(), result.getCreatedAt());
    }

    @Test
    @DisplayName(value = "[Repository] ????????? ?????? ?????? email ?????? ???????????? ???????????? DB?????? ???????????????.")
    void FindByEmailTest() throws Exception{
        ServiceUser result = loginRepository.FindByEmail("ruby.kim@codestates.com").get(0);
        ServiceUser standard = entityManager.find(ServiceUser.class, 13L);

        submit.ResultSave(result.getMobile().equals(standard.getMobile()));
        Assertions.assertEquals(standard.getPassword(), result.getPassword());
    }

    @Test
    @DisplayName(value = "[Repository] ????????? ?????? ?????? ?????? ???????????? id ?????? ???????????? DB ?????? ????????? ???????????????.")
    void CreateServiceUserTest() throws Exception{
        LoginSignUp loginSignUp = new LoginSignUp();
        loginSignUp.setEmail("Hello! My Code!");
        loginSignUp.setMobile("010-3333-4444");
        loginSignUp.setUsername("");
        loginSignUp.setPassword("");

        loginRepository.CreateServiceUser(loginSignUp, 45L);

        ServiceUser result = entityManager.find(ServiceUser.class, 45L);

        submit.ResultSave(loginSignUp.getEmail().equals(result.getEmail()));
        Assertions.assertEquals(loginSignUp.getMobile(), result.getMobile());
    }
}
