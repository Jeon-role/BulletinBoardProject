package com.board.bulletinboardproject.entityTest;


import com.board.bulletinboardproject.entity.User;
import com.board.bulletinboardproject.entity.UserRoleEnum;
import com.board.bulletinboardproject.repositoryTest.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;


@Transactional
@SpringBootTest
public class UserTest {
    @Autowired
    UserRepository userRepository;

    @Test
    @Rollback(value = false)
    @DisplayName("성공 테스트")
    void userTest1(){
        // given
        User user = new User();
        user.setUsername("park");
        user.setPassword("1234");
        UserRoleEnum role = UserRoleEnum.USER;
        user.setRole(role);


        // when
        userRepository.save(user);


        // then
        User user2 = userRepository.findByUsername(user.getUsername()).orElseThrow();
        assertEquals(user.getUsername(), user2.getUsername());
        assertEquals(user.getPassword(), user2.getPassword());
        assertEquals(user.getRole(), user2.getRole());
    }
    @Test
    @Rollback(value = false)
    @DisplayName("실패 테스트 -> username null 체크")
    void userTest2(){
        // given
        User user = new User();
        user.setPassword("4567");
        UserRoleEnum role = UserRoleEnum.USER;
        user.setRole(role);


        // when
        userRepository.save(user);


        // then
        User user2 = userRepository.findByPassword(user.getPassword()).orElseThrow();
        assertEquals(user.getPassword(), user2.getPassword());
        assertEquals(user.getRole(), user2.getRole());


    }
    @Test
    @Rollback(value = false)
    @DisplayName("실패 테스트 -> password null 체크")
    void userTest3(){
        // given
        User user = new User();
        user.setUsername("park");
        UserRoleEnum role = UserRoleEnum.USER;
        user.setRole(role);


        // when
        userRepository.save(user);


        // then
        User user2 = userRepository.findByPassword(user.getUsername()).orElseThrow();
        assertEquals(user.getUsername(), user2.getUsername());
        assertEquals(user.getRole(), user2.getRole());

    }
    @Test
    @Rollback(value = false)
    @DisplayName("실패 테스트 -> role null 체크")
    void userTest4(){
        // given
        User user = new User();
        user.setUsername("park2");
        user.setPassword("1234");


        // when
        userRepository.save(user);


        // then
        User user2 = userRepository.findByUsername(user.getUsername()).orElseThrow();
        assertEquals(user.getUsername(), user2.getUsername());
        assertEquals(user.getPassword(), user2.getPassword());

    }
    @Test
    @Rollback(value = false)
    @DisplayName("실패 테스트 -> username 중복 체크")
    void userTest5(){
        // given
        User user = new User();
        user.setUsername("park");
        user.setPassword("5678");
        UserRoleEnum role = UserRoleEnum.USER;
        user.setRole(role);


        // when
        userRepository.save(user);


        // then
        User user2 = userRepository.findByUsername(user.getUsername()).orElseThrow();
        assertEquals(user.getUsername(), user2.getUsername());
        assertEquals(user.getPassword(), user2.getPassword());
        assertEquals(user.getRole(), user2.getRole());

    }

}
