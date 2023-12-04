package com.board.bulletinboardproject.serviceTest;

import com.board.bulletinboardproject.dto.BulletinBoardRequestDto;
import com.board.bulletinboardproject.dto.BulletinBoardResponseDto;
import com.board.bulletinboardproject.dto.CommentRequestDto;
import com.board.bulletinboardproject.dto.CommentResponseDto;
import com.board.bulletinboardproject.entity.BulletinBoard;
import com.board.bulletinboardproject.entity.Comment;
import com.board.bulletinboardproject.entity.User;
import com.board.bulletinboardproject.entity.UserRoleEnum;
import com.board.bulletinboardproject.repositoryTest.BulletinBoardRepository;
import com.board.bulletinboardproject.repositoryTest.CommentRepository;
import com.board.bulletinboardproject.service.BulletinBoardService;
import com.board.bulletinboardproject.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @Mock
    CommentRepository commentRepository;
    @Mock
    BulletinBoardService bulletinBoardService;

    @Mock
    BulletinBoardRepository bulletinBoardRepository;

    @InjectMocks
    CommentService commentService;

    @Test
    @DisplayName("댓글 작성")
    void commentCreate(){
        // given
        User user = new User();
        user.setId(50L);
        user.setUsername("gggg");
        user.setPassword("1234789");
        UserRoleEnum role = UserRoleEnum.USER;
        user.setRole(role);

        BulletinBoardRequestDto bulletinBoardRequestDto = new BulletinBoardRequestDto();
        bulletinBoardRequestDto.setTitle("테스트입니다.5");
        bulletinBoardRequestDto.setContents("테스트중이에요. 5");
        bulletinBoardService = new BulletinBoardService(bulletinBoardRepository);
        BulletinBoard bulletinBoard = new BulletinBoard(bulletinBoardRequestDto, user);
        when(bulletinBoardRepository.save(any(BulletinBoard.class))).thenReturn(bulletinBoard);
        bulletinBoardService.createBulletinBoard(bulletinBoardRequestDto,user);

        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setComment("회이팅");

        commentService = new CommentService(commentRepository,bulletinBoardService);
        Comment comment = new Comment(commentRequestDto,bulletinBoard,user);



        //when
        given(bulletinBoardRepository.findById(bulletinBoard.getId())).willReturn(Optional.of(bulletinBoard));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        CommentResponseDto commentResponseDto = commentService.createComment(bulletinBoard.getId(),commentRequestDto,user);


        //then
        verify(commentRepository).save(any(Comment.class));
        assertEquals("회이팅",commentResponseDto.getComment());
        assertEquals("gggg",commentResponseDto.getUsername());
    }
    @Test
    @DisplayName("댓글 수정")
    void commentUpdate(){
        // given
        User user = new User();
        user.setId(50L);
        user.setUsername("gggg");
        user.setPassword("1234789");
        UserRoleEnum role = UserRoleEnum.USER;
        user.setRole(role);

        BulletinBoardRequestDto bulletinBoardRequestDto = new BulletinBoardRequestDto();
        bulletinBoardRequestDto.setTitle("테스트입니다.5");
        bulletinBoardRequestDto.setContents("테스트중이에요. 5");
        bulletinBoardService = new BulletinBoardService(bulletinBoardRepository);
        BulletinBoard bulletinBoard = new BulletinBoard(bulletinBoardRequestDto, user);
        when(bulletinBoardRepository.save(any(BulletinBoard.class))).thenReturn(bulletinBoard);
        bulletinBoardService.createBulletinBoard(bulletinBoardRequestDto,user);

        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setComment("회이팅");

        commentService = new CommentService(commentRepository,bulletinBoardService);
        Comment comment = new Comment(commentRequestDto,bulletinBoard,user);

        given(bulletinBoardRepository.findById(bulletinBoard.getId())).willReturn(Optional.of(bulletinBoard));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        commentService.createComment(bulletinBoard.getId(),commentRequestDto,user);

        CommentRequestDto commentRequestDtoChange = new CommentRequestDto();
        commentRequestDtoChange.setComment("회이팅 대박 수정");
        Comment commentChange = new Comment(commentRequestDtoChange,bulletinBoard,user);


        //when
        given(bulletinBoardRepository.findById(bulletinBoard.getId())).willReturn(Optional.of(bulletinBoard));
        given(commentRepository.findById(comment.getId())).willReturn(Optional.of(comment));
        CommentResponseDto responseDto = commentService.updateComment(bulletinBoard.getId(), commentChange.getId(),commentRequestDtoChange,user);


        //then
        assertEquals("회이팅 대박 수정",responseDto.getComment());
        assertEquals("gggg",responseDto.getUsername());


    }

    @Test
    @DisplayName("댓글 삭제")
    void commentDelete(){
        // given
        User user = new User();
        user.setId(50L);
        user.setUsername("gggg");
        user.setPassword("1234789");
        UserRoleEnum role = UserRoleEnum.USER;
        user.setRole(role);

        BulletinBoardRequestDto bulletinBoardRequestDto = new BulletinBoardRequestDto();
        bulletinBoardRequestDto.setTitle("테스트입니다.5");
        bulletinBoardRequestDto.setContents("테스트중이에요. 5");
        bulletinBoardService = new BulletinBoardService(bulletinBoardRepository);
        BulletinBoard bulletinBoard = new BulletinBoard(bulletinBoardRequestDto, user);
        when(bulletinBoardRepository.save(any(BulletinBoard.class))).thenReturn(bulletinBoard);
        bulletinBoardService.createBulletinBoard(bulletinBoardRequestDto,user);

        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setComment("회이팅");

        commentService = new CommentService(commentRepository,bulletinBoardService);
        Comment comment = new Comment(commentRequestDto,bulletinBoard,user);

        given(bulletinBoardRepository.findById(bulletinBoard.getId())).willReturn(Optional.of(bulletinBoard));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        commentService.createComment(bulletinBoard.getId(),commentRequestDto,user);


        //when
        given(commentRepository.findById(comment.getId())).willReturn(Optional.of(comment));
        commentService.deleteComment(comment.getId(), user);


        //then
        verify(commentRepository).delete(any(Comment.class));

    }




}
