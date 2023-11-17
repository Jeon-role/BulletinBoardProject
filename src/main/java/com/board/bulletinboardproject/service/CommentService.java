package com.board.bulletinboardproject.service;

import com.board.bulletinboardproject.dto.CommentRequestDto;
import com.board.bulletinboardproject.dto.CommentResponseDto;
import com.board.bulletinboardproject.dto.StatusDto;
import com.board.bulletinboardproject.entity.BulletinBoard;
import com.board.bulletinboardproject.entity.Comment;
import com.board.bulletinboardproject.entity.User;
import com.board.bulletinboardproject.jwt.JwtUtil;
import com.board.bulletinboardproject.repository.BulletinBoardRepository;
import com.board.bulletinboardproject.repository.CommentRepository;
import com.board.bulletinboardproject.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final BulletinBoardRepository bulletinBoardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    public CommentService(CommentRepository commentRepository,BulletinBoardRepository bulletinBoardRepository,UserRepository userRepository,JwtUtil jwtUtil){
        this.commentRepository=commentRepository;
        this.bulletinBoardRepository=bulletinBoardRepository;
        this.userRepository=userRepository;
        this.jwtUtil=jwtUtil;
    }

    public CommentResponseDto createComment(Long id, CommentRequestDto commentRequestDto, HttpServletRequest request) {

        String headerToken= request.getHeader(JwtUtil.AUTHORIZATION_HEADER);

        String token = jwtUtil.substringToken(headerToken);

        Claims claims;
        if(token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("token Error");
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(NullPointerException::new);


            BulletinBoard board = bulletinBoardRepository.findById(id).orElseThrow(() ->
                    new IllegalArgumentException("선택한 게시판은 존재하지 않습니다. 다시해주세요 "));

            Comment comment = commentRepository.save(new Comment(commentRequestDto,board,user));

            return new CommentResponseDto(comment,user);


        }
        else {
            return null;
        }

    }



    @Transactional
    public CommentResponseDto updateComment(Long board_id,Long id, CommentRequestDto commentRequestDto, HttpServletRequest request) {

        String headerToken= request.getHeader(JwtUtil.AUTHORIZATION_HEADER);

        String token = jwtUtil.substringToken(headerToken);

        Claims claims;
        if(token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("token Error");
            }
            User user =userRepository.findByUsername(claims.getSubject()).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
            bulletinBoardRepository.findById(board_id).orElseThrow(() ->
                    new IllegalArgumentException("선택한 게시판은 존재하지 않습니다. 다시해주세요 "));
            Comment comment = commentRepository.findById(id).orElseThrow(() ->
                    new IllegalArgumentException("선택한 댓글은 존재하지 않습니다. 다시해주세요 "));
            if(user.getUsername().equals(comment.getUsername())){
                comment.update(commentRequestDto);
                return new CommentResponseDto(comment);
            }
            else {
                throw new IllegalArgumentException("작성자가 일치하지 않습니다.");
            }




        }
        else {
            return null;
        }

    }

    public StatusDto deleteComment(Long id, HttpServletRequest request) {
        String headerToken= request.getHeader(JwtUtil.AUTHORIZATION_HEADER);

        String token = jwtUtil.substringToken(headerToken);

        Claims claims;
        if(token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("token Error");
            }

            User user =userRepository.findByUsername(claims.getSubject()).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
            Comment comment = commentRepository.findById(id).orElseThrow(() ->
                    new IllegalArgumentException("선택한 댓글은 존재하지 않습니다. 다시해주세요 "));
            if(user.getUsername().equals(comment.getUsername())){
                commentRepository.delete(comment);
                return new StatusDto("삭제 성공", HttpStatusCode.valueOf(200).toString());
            }
            else {
                throw new IllegalArgumentException("작성자가 일치하지 않습니다.");
            }
        }
        else {
            return null;
        }

    }
}
