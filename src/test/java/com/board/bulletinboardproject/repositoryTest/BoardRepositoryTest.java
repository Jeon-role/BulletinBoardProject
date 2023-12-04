package com.board.bulletinboardproject.repositoryTest;

import com.board.bulletinboardproject.entity.BulletinBoard;
import com.board.bulletinboardproject.entity.User;
import com.board.bulletinboardproject.entity.UserRoleEnum;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.annotation.Rollback;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BoardRepositoryTest {

    @Autowired
    BulletinBoardRepository bulletinBoardRepository2;

    @Autowired
    UserRepository userRepository;


    @Test
    @Rollback(value = false)
    @DisplayName("저장 테스트")
    void boardSaveTest(){
        //given
        User user = new User();
        user.setUsername("mayday2");
        user.setPassword("12345678");
        UserRoleEnum role = UserRoleEnum.USER;
        user.setRole(role);


        BulletinBoard board = new BulletinBoard();
        board.setTitle("테스트 10");
        board.setContents("테스트 중입니다.10");
        board.setUser(user);


        //when
        userRepository.save(user);
        bulletinBoardRepository2.save(board);

        // then
        BulletinBoard bulletinBoard = bulletinBoardRepository2.findByTitle(board.getTitle()).orElseThrow();
        assertEquals(board.getTitle(),bulletinBoard.getTitle());
        assertEquals(board.getContents(),bulletinBoard.getContents());
        assertEquals(board.getUser(),bulletinBoard.getUser());

    }


    @Test
    @Rollback(value = false)
    @DisplayName("조회 테스트")
    void boardFindTitle(){
        //given
        //when
        BulletinBoard bulletinBoard = bulletinBoardRepository2.findByTitle("테스트 10").orElseThrow();
        User user = userRepository.findByUsername("mayday2").orElseThrow();


        //then
        assertEquals("테스트 10",bulletinBoard.getTitle());
        assertEquals("테스트 중입니다.10",bulletinBoard.getContents());
        assertEquals(user,bulletinBoard.getUser());
    }


    @Test
    @Rollback(value = false)
    @DisplayName("삭제 테스트")
    void boardDelete(){
        //given
        String nullValue="테스트";
        BulletinBoard bulletinBoard = bulletinBoardRepository2.findByTitle("테스트 10").orElseThrow();
        User user = userRepository.findByUsername("mayday2").orElseThrow();



        //when
        bulletinBoardRepository2.delete(bulletinBoard);
        userRepository.delete(user);
        Optional<BulletinBoard> bulletinBoard2 = bulletinBoardRepository2.findByTitle("테스트 10");
        Optional<User> user2 = userRepository.findByUsername("mayday2");

        if(bulletinBoard2.isEmpty() && user2.isEmpty()){
            nullValue=null;
        }

        //then
        assertNull(nullValue);

    }




}
