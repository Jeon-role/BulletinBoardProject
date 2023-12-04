package com.board.bulletinboardproject.entityTest;

import com.board.bulletinboardproject.entity.BulletinBoard;
import com.board.bulletinboardproject.entity.User;
import com.board.bulletinboardproject.entity.UserRoleEnum;
import com.board.bulletinboardproject.repositoryTest.BulletinBoardRepository;
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
public class BoardTest {

    @Autowired
    BulletinBoardRepository bulletinBoardRepository;
    @Autowired
    UserRepository userRepository;


    @Test
    @Rollback(value = false)
    @DisplayName("성공 테스트")
    void BoardTest1(){
        // given
        User user = new User();
        user.setUsername("kang");
        user.setPassword("1234");
        UserRoleEnum role = UserRoleEnum.USER;
        user.setRole(role);



        BulletinBoard board = new BulletinBoard();
        board.setTitle("테스트 1");
        board.setContents("테스트 중입니다.");
        board.setUser(user);

        // when
        userRepository.save(user);
        bulletinBoardRepository.save(board);


        // then
        BulletinBoard bulletinBoard = bulletinBoardRepository.findByTitle(board.getTitle()).orElseThrow();
        assertEquals(board.getTitle(),bulletinBoard.getTitle());
        assertEquals(board.getContents(),bulletinBoard.getContents());
        assertEquals(board.getUser(),bulletinBoard.getUser());


    }
    @Test
    @Rollback(value = false)
    @DisplayName("실패 테스트 -> title null 체크")
    void BoardTest2(){
        // given

        User user = new User();
        user.setUsername("Jang");
        user.setPassword("1234");
        UserRoleEnum role = UserRoleEnum.USER;
        user.setRole(role);



        BulletinBoard board = new BulletinBoard();
        board.setContents("테스트 중입니다.");
        board.setUser(user);

        // when
        userRepository.save(user);
        bulletinBoardRepository.save(board);


        // then
        BulletinBoard bulletinBoard = bulletinBoardRepository.findByTitle(board.getTitle()).orElseThrow();
        assertEquals(board.getContents(),bulletinBoard.getContents());
        assertEquals(board.getUser(),bulletinBoard.getUser());

    }
    @Test
    @Rollback(value = false)
    @DisplayName("실패 테스트 -> contents null 체크")
    void BoardTest3(){
        // given
        User user = new User();
        user.setUsername("qwer");
        user.setPassword("1234");
        UserRoleEnum role = UserRoleEnum.USER;
        user.setRole(role);



        BulletinBoard board = new BulletinBoard();
        board.setTitle("테스트 1");
        board.setUser(user);

        // when
        userRepository.save(user);
        bulletinBoardRepository.save(board);


        // then
        BulletinBoard bulletinBoard = bulletinBoardRepository.findByTitle(board.getTitle()).orElseThrow();
        assertEquals(board.getTitle(),bulletinBoard.getTitle());
        assertEquals(board.getUser(),bulletinBoard.getUser());

    }
    @Test
    @Rollback(value = false)
    @DisplayName("실패 테스트 -> user null 체크")
    void BoardTest4(){
        // given

        BulletinBoard board = new BulletinBoard();
        board.setTitle("테스트 1");
        board.setContents("테스트 중입니다.");


        // when
        bulletinBoardRepository.save(board);


        // then
        BulletinBoard bulletinBoard = bulletinBoardRepository.findByTitle(board.getTitle()).orElseThrow();
        assertEquals(board.getTitle(),bulletinBoard.getTitle());
        assertEquals(board.getContents(),bulletinBoard.getContents());
    }


}
