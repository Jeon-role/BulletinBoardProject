package com.board.bulletinboardproject.controller;

import com.board.bulletinboardproject.dto.LoginRequestDto;
import com.board.bulletinboardproject.dto.MsgStatusDto;
import com.board.bulletinboardproject.dto.SignupRequestDto;
import com.board.bulletinboardproject.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/signup")
    public MsgStatusDto signup(@RequestBody SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }

    @PostMapping("/login")
    public MsgStatusDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse res){
        return userService.login(loginRequestDto,res);
    }
}
