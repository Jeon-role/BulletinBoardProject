package com.board.bulletinboardproject.controllerTest;

import com.board.bulletinboardproject.MockSpringSecurityFilter;
import com.board.bulletinboardproject.config.WebSecurityConfig;
import com.board.bulletinboardproject.controller.UserController;
import com.board.bulletinboardproject.dto.SignupRequestDto;
import com.board.bulletinboardproject.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;



import static org.mockito.Mockito.verify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



//@WebMvcTest(
//        controllers = {UserController.class},
//        excludeFilters = {
//                @ComponentScan.Filter(
//                        type = FilterType.ASSIGNABLE_TYPE,
//                        classes = WebSecurityConfig.class
//                )
//        }
//)
@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

//    @Autowired
//    private WebApplicationContext context;


    @MockBean
    UserService userService;



//    @Autowired
    private ObjectMapper objectMapper =new ObjectMapper();






    @BeforeEach
    void init() {
        mvc=MockMvcBuilders.standaloneSetup(UserController.class).build();
    }





    @Test
    @DisplayName("회원 가입")
    void signupControllerTest() throws Exception {

        String username="jangjang";
        String password="12345678";

        SignupRequestDto req =new SignupRequestDto(username,password,false,"");

        String postInfo = objectMapper.writeValueAsString(req);

        mvc.perform(
                        post("/api/user/signup")
                                .content(postInfo)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(userService).signup(req);




    }

}
