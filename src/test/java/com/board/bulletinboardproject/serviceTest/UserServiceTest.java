package com.board.bulletinboardproject.serviceTest;

import com.board.bulletinboardproject.dto.LoginRequestDto;
import com.board.bulletinboardproject.dto.SignupRequestDto;
import com.board.bulletinboardproject.entity.User;
import com.board.bulletinboardproject.repositoryTest.UserRepository;
import com.board.bulletinboardproject.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 성공")
    void signupTest(){
        // given
        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUsername("qwerty");
        signupRequestDto.setPassword("12345678");
        userService = new UserService(userRepository,passwordEncoder);

        // when
        userService.signup(signupRequestDto);

        // then
        verify(userRepository).save(any());
    }

    @Test
    @DisplayName("로그인 성공")
    void loginTest(){
        // given
        User user =new User();
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUsername("qwerty");
        String psw="12345678";
        String password = passwordEncoder.encode(psw);
        loginRequestDto.setPassword(psw);
        userService = new UserService(userRepository,passwordEncoder);

        given(userRepository.findByUsername(loginRequestDto.getUsername())).willReturn(Optional.of(user));
        given(passwordEncoder.matches(loginRequestDto.getPassword(),password)).willReturn(true);

        //when
        userService.login(loginRequestDto);



        //then
        //없음

    }


}
