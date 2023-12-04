package com.board.bulletinboardproject.service;

import com.board.bulletinboardproject.dto.LoginRequestDto;
import com.board.bulletinboardproject.dto.SignupRequestDto;
import com.board.bulletinboardproject.entity.User;
import com.board.bulletinboardproject.entity.UserRoleEnum;
import com.board.bulletinboardproject.repositoryTest.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";



    public void signup(SignupRequestDto signupRequestDto) {
        System.out.println("지나감 2");
        System.out.println("signupRequestDto.getUsername() = " + signupRequestDto.getUsername());
        System.out.println("signupRequestDto.getPassword() = " + signupRequestDto.getPassword());
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
    }
    public void login(LoginRequestDto loginRequestDto){
        String username=loginRequestDto.getUsername();
        String password=loginRequestDto.getPassword();

        User user= userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

//        UserLogout userLogout= new UserLogout();
//        userLogout.setUser(user);
//        userLogoutRepository.save(userLogout);


    }

//    public ResponseEntity<StatusDto> logout(HttpServletRequest request) {
//
//
//        String headerToken= request.getHeader(JwtUtil.AUTHORIZATION_HEADER);
//        String token = jwtUtil.substringToken(headerToken);
//        Claims claims;
//        if(token != null) {
//            if (jwtUtil.validateToken(token)) {
//                claims = jwtUtil.getUserInfoFromToken(token);
//            } else {
//                throw new IllegalArgumentException("token Error");
//            }
//            User user =userRepository.findByUsername(claims.getSubject()).orElseThrow(NullPointerException::new);
//
//            UserLogout userLogout= userLogoutRepository.findByUser(user);
//            userLogoutRepository.delete(userLogout);
//
//            return ResponseEntity.ok(new StatusDto("로그아웃 성공", HttpStatusCode.valueOf(200).toString()));
//        }
//        else {
//            return null;
//        }
//    }



}
