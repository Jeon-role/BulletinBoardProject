package com.board.bulletinboardproject.service;

import com.board.bulletinboardproject.dto.CommentRequestDto;
import com.board.bulletinboardproject.dto.CommentResponseDto;
import com.board.bulletinboardproject.entity.BulletinBoard;
import com.board.bulletinboardproject.entity.Comment;
import com.board.bulletinboardproject.entity.User;
import com.board.bulletinboardproject.repositoryTest.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final BulletinBoardService bulletinBoardService;


    public CommentService(CommentRepository commentRepository,BulletinBoardService bulletinBoardService){
        this.commentRepository=commentRepository;
        this.bulletinBoardService=bulletinBoardService;
    }

    public CommentResponseDto createComment(Long id, CommentRequestDto commentRequestDto, User user) {
        BulletinBoard board = bulletinBoardService.findId(id);
        Comment comment = commentRepository.save(new Comment(commentRequestDto,board,user));
        return new CommentResponseDto(comment);
    }



    @Transactional
    public CommentResponseDto updateComment(Long board_id,Long id, CommentRequestDto commentRequestDto, User user) {
        bulletinBoardService.findId(board_id);
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 댓글은 존재하지 않습니다. 다시해주세요 "));
        if(user.getUsername().equals(comment.getUser().getUsername())){
            comment.update(commentRequestDto);
            return new CommentResponseDto(comment);
        }
        else {
            throw new IllegalArgumentException("작성자가 일치하지 않습니다.");
        }
    }

    public void deleteComment(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 댓글은 존재하지 않습니다. 다시해주세요 "));
        if(user.getUsername().equals(comment.getUser().getUsername())){
            commentRepository.delete(comment);
        }
        else {
            throw new IllegalArgumentException("작성자가 일치하지 않습니다.");
        }
    }
}
