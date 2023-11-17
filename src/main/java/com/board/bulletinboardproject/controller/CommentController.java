package com.board.bulletinboardproject.controller;


import com.board.bulletinboardproject.dto.CommentRequestDto;
import com.board.bulletinboardproject.dto.CommentResponseDto;
import com.board.bulletinboardproject.dto.StatusDto;
import com.board.bulletinboardproject.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boards")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService=commentService;
    }

    @PostMapping("/{id}/comment")
    public CommentResponseDto createComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request){
        return commentService.createComment(id,commentRequestDto,request);
    }

    @PutMapping("/{board_id}/comment/{id}")
    public CommentResponseDto updateComment(@PathVariable Long board_id,@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, HttpServletRequest request){
        return commentService.updateComment(board_id,id,commentRequestDto,request);
    }


    @DeleteMapping("/comment/{id}")
    public StatusDto deleteComment(@PathVariable Long id,HttpServletRequest request){
        return commentService.deleteComment(id,request);
    }

}
