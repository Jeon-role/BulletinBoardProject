package com.board.bulletinboardproject.repositoryTest;

import com.board.bulletinboardproject.entity.User;
import com.board.bulletinboardproject.entity.UserRoleEnum;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository2;




    @Test
    @Order(1)
    @Rollback(value = false)
    @DisplayName("저장 테스트")
    void RepoSaveTest(){
        //given
        User user = new User();
        user.setUsername("mayday");
        user.setPassword("12345678");
        UserRoleEnum role = UserRoleEnum.USER;
        user.setRole(role);


        // when
        userRepository2.save(user);



        // then
        User user2 = userRepository2.findByUsername(user.getUsername()).orElseThrow();
        assertEquals(user.getUsername(), user2.getUsername());
        assertEquals(user.getPassword(), user2.getPassword());
        assertEquals(user.getRole(), user2.getRole());

    }

    @Test
    @Order(2)
    @Rollback(value = false)
    @DisplayName("조회(username로) 테스트")
    void RepoGetUserNameTest(){
        // when
        User user =  userRepository2.findByUsername("mayday").orElseThrow(()-> new IllegalArgumentException("사용자가 없어요"));

        // then
        assertEquals("mayday",user.getUsername());
        assertEquals("12345678",user.getPassword());

    }




    @Test
    @Order(3)
    @Rollback(value = false)
    @DisplayName("삭제 테스트")
    void RepoDeleteTest(){
        //given
        String nullValue="테스트";
        //when
        User user =  userRepository2.findByUsername("mayday").orElseThrow(()-> new IllegalArgumentException("사용자가 없어요"));
        userRepository2.delete(user);
        Optional<User> user2 =  userRepository2.findByUsername("mayday");
        if(user2.isEmpty()){
            nullValue=null;
        }


        //then
        assertNull(nullValue);

    }



}
