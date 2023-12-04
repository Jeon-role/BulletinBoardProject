package com.board.bulletinboardproject.entityTest;

import com.board.bulletinboardproject.entity.BulletinBoard;
import com.board.bulletinboardproject.entity.Comment;
import com.board.bulletinboardproject.entity.User;
import com.board.bulletinboardproject.entity.UserRoleEnum;
import com.board.bulletinboardproject.repositoryTest.BulletinBoardRepository;
import com.board.bulletinboardproject.repositoryTest.CommentRepository;
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
public class CommentTest {
    @Autowired
    BulletinBoardRepository bulletinBoardRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CommentRepository commentRepository;

    @Test
    @Rollback(value = false)
    @DisplayName("성공 테스트")
    void CommentTest1(){
        // given
        User user = new User();
        user.setUsername("kimje");
        user.setPassword("1234");
        UserRoleEnum role = UserRoleEnum.USER;
        user.setRole(role);



        BulletinBoard board = new BulletinBoard();
        board.setTitle("테스트 2");
        board.setContents("테스트 중입니다.2");
        board.setUser(user);

        Comment comment =new Comment();
        comment.setComment("화이팅 하세요");
        comment.setUser(user);
        comment.setBoard(board);


        // when
        userRepository.save(user);
        bulletinBoardRepository.save(board);
        commentRepository.save(comment);


        // then
        Comment comment2  = commentRepository.findByComment(comment.getComment()).orElseThrow();
        assertEquals(comment.getComment(),comment2.getComment());
        assertEquals(comment.getUser(),comment2.getUser());
        assertEquals(comment.getBoard(),comment2.getBoard());
    }
    @Test
    @Rollback(value = false)
    @DisplayName("실패 테스트 -> comment null 체크")
    void CommentTest2(){
        // given
        User user = new User();
        user.setUsername("kimjo");
        user.setPassword("1234");
        UserRoleEnum role = UserRoleEnum.USER;
        user.setRole(role);



        BulletinBoard board = new BulletinBoard();
        board.setTitle("테스트 3");
        board.setContents("테스트 중입니다.3");
        board.setUser(user);

        Comment comment =new Comment();
        comment.setUser(user);
        comment.setBoard(board);


        // when
        userRepository.save(user);
        bulletinBoardRepository.save(board);
        commentRepository.save(comment);


        // then
        Comment comment2  = commentRepository.findByComment(comment.getComment()).orElseThrow();
        assertEquals(comment.getUser(),comment2.getUser());
        assertEquals(comment.getBoard(),comment2.getBoard());

    }
    @Test
    @Rollback(value = false)
    @DisplayName("실패 테스트 -> board null 체크")
    void CommentTest3(){
        // given
        User user = new User();
        user.setUsername("sang");
        user.setPassword("1234");
        UserRoleEnum role = UserRoleEnum.USER;
        user.setRole(role);




        Comment comment =new Comment();
        comment.setComment("화이팅 하세요");
        comment.setUser(user);



        // when
        userRepository.save(user);
        commentRepository.save(comment);


        // then
        Comment comment2  = commentRepository.findByComment(comment.getComment()).orElseThrow();
        assertEquals(comment.getComment(),comment2.getComment());
        assertEquals(comment.getUser(),comment2.getUser());
    }
    @Test
    @Rollback(value = false)
    @DisplayName("실패 테스트 -> user null 체크")
    void CommentTest4(){
        // given
        User user = new User();
        user.setUsername("sang");
        user.setPassword("1234");
        UserRoleEnum role = UserRoleEnum.USER;
        user.setRole(role);



        BulletinBoard board = new BulletinBoard();
        board.setTitle("테스트 2");
        board.setContents("테스트 중입니다.2");
        board.setUser(user);

        Comment comment =new Comment();
        comment.setComment("화이팅 하세요");
        comment.setBoard(board);


        // when
        userRepository.save(user);
        bulletinBoardRepository.save(board);
        commentRepository.save(comment);


        // then
        Comment comment2  = commentRepository.findByComment(comment.getComment()).orElseThrow();
        assertEquals(comment.getComment(),comment2.getComment());
        assertEquals(comment.getBoard(),comment2.getBoard());
    }

}
