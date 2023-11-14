package com.board.bulletinboardproject.service;

import com.board.bulletinboardproject.dto.LoginRequestDto;
import com.board.bulletinboardproject.dto.MsgStatusDto;
import com.board.bulletinboardproject.dto.SignupRequestDto;
import com.board.bulletinboardproject.entity.User;
import com.board.bulletinboardproject.entity.UserRoleEnum;
import com.board.bulletinboardproject.jwt.JwtUtil;
import com.board.bulletinboardproject.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";



    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder,JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil=jwtUtil;
    }
    public MsgStatusDto signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }


        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(signupRequestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }



        User user = new User(username, password, role);
        userRepository.save(user);
        return new MsgStatusDto("가입성공", HttpStatusCode.valueOf(200).toString());

    }
    public MsgStatusDto login(LoginRequestDto loginRequestDto, HttpServletResponse res){
        String username=loginRequestDto.getUsername();
        String password=loginRequestDto.getPassword();

        User user= userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );


        //비밀번호 확인
        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        //JWT 생성 및 쿠키게 저장후 response 객체에 추가
        String token= jwtUtil.createToken(user.getUsername(),user.getRole());
        jwtUtil.addJwtToCookie(token,res);

        return new MsgStatusDto("로그인성공", HttpStatusCode.valueOf(200).toString());
    }


}
