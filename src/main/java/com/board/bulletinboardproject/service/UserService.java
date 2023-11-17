package com.board.bulletinboardproject.service;

import com.board.bulletinboardproject.dto.LoginRequestDto;
import com.board.bulletinboardproject.dto.SignupRequestDto;
import com.board.bulletinboardproject.dto.StatusDto;
import com.board.bulletinboardproject.entity.User;
import com.board.bulletinboardproject.entity.UserRoleEnum;
import com.board.bulletinboardproject.jwt.JwtUtil;
import com.board.bulletinboardproject.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<StatusDto> signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
//            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
            return ResponseEntity.ok(new StatusDto("중복된 username 입니다", HttpStatusCode.valueOf(400).toString()));
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
        return ResponseEntity.ok(new StatusDto("가입성공", HttpStatusCode.valueOf(200).toString()));

    }
    public ResponseEntity<StatusDto> login(LoginRequestDto loginRequestDto, HttpServletResponse res){
        String username=loginRequestDto.getUsername();
        String password=loginRequestDto.getPassword();




        User user= userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        if(!user.getUsername().equals(username)){
            return ResponseEntity.ok(new StatusDto("회원을 찾을 수 없습니다", HttpStatusCode.valueOf(400).toString()));
        }



        if(!passwordEncoder.matches(password,user.getPassword())){
            return ResponseEntity.ok(new StatusDto("회원을 찾을 수 없습니다", HttpStatusCode.valueOf(400).toString()));
//            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }


        String token= jwtUtil.createToken(user.getUsername(),user.getRole());
        res.addHeader(JwtUtil.AUTHORIZATION_HEADER,token);


        return ResponseEntity.ok(new StatusDto("로그인성공", HttpStatusCode.valueOf(200).toString()));
    }


}
