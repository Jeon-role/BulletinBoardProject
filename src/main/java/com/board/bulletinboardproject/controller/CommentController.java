package com.board.bulletinboardproject.controller;


import com.board.bulletinboardproject.dto.CommentRequestDto;
import com.board.bulletinboardproject.dto.CommentResponseDto;
import com.board.bulletinboardproject.dto.StatusDto;
import com.board.bulletinboardproject.jwt.UserDetailsImpl;
import com.board.bulletinboardproject.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@RestController
@RequestMapping("/api/boards")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService=commentService;
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<StatusDto> createComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        CommentResponseDto commentResponseDto = commentService.createComment(id,commentRequestDto,userDetails.getUser());
        return ResponseEntity.status(201).body(commentResponseDto);
    }

    @PutMapping("/{board_id}/comment/{id}")
    public ResponseEntity<StatusDto> updateComment(@PathVariable Long board_id,@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto,@AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            CommentResponseDto commentResponseDto = commentService.updateComment(board_id,id,commentRequestDto,userDetails.getUser());
            return ResponseEntity.ok().body(commentResponseDto);
        } catch (RejectedExecutionException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new StatusDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }


    @DeleteMapping("/comment/{id}")
    public ResponseEntity<StatusDto> deleteComment(@PathVariable Long id,@AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            commentService.deleteComment(id,userDetails.getUser());
            return ResponseEntity.ok().body(new StatusDto("삭제 성공.", HttpStatus.OK.value()));
        } catch (RejectedExecutionException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new StatusDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

}
