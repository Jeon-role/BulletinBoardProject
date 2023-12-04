package com.board.bulletinboardproject.controller;

import com.board.bulletinboardproject.dto.LoginRequestDto;
import com.board.bulletinboardproject.dto.SignupRequestDto;
import com.board.bulletinboardproject.dto.StatusDto;
import com.board.bulletinboardproject.jwt.JwtUtil;
import com.board.bulletinboardproject.service.UserService;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;


    public UserController(UserService userService,JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil=jwtUtil;
    }


    @PostMapping("/signup")
    public ResponseEntity<StatusDto> signup(@RequestBody SignupRequestDto signupRequestDto) {
        try {
            System.out.println("지나감 1");
            userService.signup(signupRequestDto);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(new StatusDto("중복된 username 입니다.", HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.status(HttpStatus.CREATED.value())
                .body(new StatusDto("회원가입 성공", HttpStatus.CREATED.value()));
    }

    @PostMapping("/login")
    public ResponseEntity<StatusDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse res){
        try {
            userService.login(loginRequestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new StatusDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

        res.setHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(loginRequestDto.getUsername()));

        return ResponseEntity.ok().body(new StatusDto("로그인 성공", HttpStatus.OK.value()));
    }

//    @PostMapping("/logout")
//    public ResponseEntity<StatusDto> logout(HttpServletRequest request){
//        return userService.logout(request);
//    }


}
