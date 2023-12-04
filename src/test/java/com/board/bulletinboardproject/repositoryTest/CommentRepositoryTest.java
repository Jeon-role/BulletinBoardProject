package com.board.bulletinboardproject.repositoryTest;


import com.board.bulletinboardproject.entity.BulletinBoard;
import com.board.bulletinboardproject.entity.Comment;
import com.board.bulletinboardproject.entity.User;
import com.board.bulletinboardproject.entity.UserRoleEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CommentRepositoryTest {

    @Autowired
    BulletinBoardRepository bulletinBoardRepository2;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;


    @Test
    @Rollback(value = false)
    @DisplayName("저장 테스트")
    void CommentSaveTest(){
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

        Comment comment =new Comment();
        comment.setComment("화이팅 하세요");
        comment.setUser(user);
        comment.setBoard(board);



        //when
        userRepository.save(user);
        bulletinBoardRepository2.save(board);
        commentRepository.save(comment);

        // then
        Comment comment2  = commentRepository.findByComment(comment.getComment()).orElseThrow();
        assertEquals(comment.getComment(),comment2.getComment());
        assertEquals(comment.getUser(),comment2.getUser());
        assertEquals(comment.getBoard(),comment2.getBoard());

    }

    @Test
    @Rollback(value = false)
    @DisplayName("조회 테스트")
    void commentGetCommentTest(){
        //given
        //when
        BulletinBoard bulletinBoard = bulletinBoardRepository2.findByTitle("테스트 10").orElseThrow();
        User user = userRepository.findByUsername("mayday2").orElseThrow();
        Comment comment = commentRepository.findByComment("화이팅 하세요").orElseThrow();


        //then
        assertEquals(bulletinBoard,comment.getBoard());
        assertEquals(user,comment.getUser());
        assertEquals("화이팅 하세요",comment.getComment());

    }

    @Test
    @Rollback(value = false)
    @DisplayName("삭제 테스트")
    void commentDeleteTest(){
        //given
        String nullValue="테스트";
        BulletinBoard bulletinBoard = bulletinBoardRepository2.findByTitle("테스트 10").orElseThrow();
        User user = userRepository.findByUsername("mayday2").orElseThrow();
        Comment comment = commentRepository.findByComment("화이팅 하세요").orElseThrow();




        //when
        commentRepository.delete(comment);
        bulletinBoardRepository2.delete(bulletinBoard);
        userRepository.delete(user);
        Optional<BulletinBoard> bulletinBoard2 = bulletinBoardRepository2.findByTitle("테스트 10");
        Optional<User> user2 = userRepository.findByUsername("mayday2");
        Optional<Comment> comment2 = commentRepository.findByComment("화이팅 하세요");

        if(bulletinBoard2.isEmpty() && user2.isEmpty() && comment2.isEmpty()){
            nullValue=null;
        }

        //then
        assertNull(nullValue);
    }


}
