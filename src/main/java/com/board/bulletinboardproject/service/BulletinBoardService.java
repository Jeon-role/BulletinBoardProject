package com.board.bulletinboardproject.service;


import com.board.bulletinboardproject.dto.BulletinBoardRequestDto;
import com.board.bulletinboardproject.dto.BulletinBoardResponseDto;
import com.board.bulletinboardproject.dto.CommentResponseDto;
import com.board.bulletinboardproject.entity.BulletinBoard;
import com.board.bulletinboardproject.entity.Comment;
import com.board.bulletinboardproject.entity.User;
import com.board.bulletinboardproject.jwt.JwtUtil;
import com.board.bulletinboardproject.repository.BulletinBoardRepository;
import com.board.bulletinboardproject.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class BulletinBoardService {

    private final BulletinBoardRepository bulletinBoardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public BulletinBoardService(BulletinBoardRepository bulletinBoardRepository,UserRepository userRepository ,JwtUtil jwtUtil){
        this.bulletinBoardRepository=bulletinBoardRepository;
        this.userRepository=userRepository;
        this.jwtUtil=jwtUtil;
    }

    public BulletinBoardResponseDto createBulletinBoard(BulletinBoardRequestDto bulletinBoardRequestDto, HttpServletRequest request){
        String headerToken= request.getHeader(JwtUtil.AUTHORIZATION_HEADER);

        String token = jwtUtil.substringToken(headerToken);

        Claims claims;
        if(token != null){
            if(jwtUtil.validateToken(token)){
                claims= jwtUtil.getUserInfoFromToken(token);
            }
            else {
                throw new IllegalArgumentException("token Error");
            }
            User user =userRepository.findByUsername(claims.getSubject()).orElseThrow(NullPointerException::new);
            bulletinBoardRequestDto.setUsername(user.getUsername());

            BulletinBoard bulletinBoard = new BulletinBoard(bulletinBoardRequestDto);

            BulletinBoard saveBulletinBoard = bulletinBoardRepository.save(bulletinBoard);
            BulletinBoardResponseDto bulletinBoardResponseDto = new BulletinBoardResponseDto(saveBulletinBoard);

            return bulletinBoardResponseDto;
        }
        else {
            return null;
        }
    }

    public List<BulletinBoardResponseDto> getBulletinBoards(){
        String sortBy="username";
        boolean isAsc=false;
        int page=0;
        int size=20;
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction,sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<BulletinBoard> boardList;




        boardList=bulletinBoardRepository.findAll(pageable);


//        User user =userRepository.findByUsername("hoon").orElseThrow(()->
//                new IllegalArgumentException("없어요"));
//        List<BulletinBoard> bulletinBoardList = bulletinBoardRepository.findAllByUsername(user.getUsername());



        List<BulletinBoard> bulletinBoardList = bulletinBoardRepository.findAllByOrderByModifiedAtDesc();
        List<BulletinBoardResponseDto> responseDtoList = new ArrayList<>();



        for(BulletinBoard bulletinBoard : boardList){
            List<CommentResponseDto> commentList= new ArrayList<>();
            for(Comment comment :bulletinBoard.getComments()){
                commentList.add(new CommentResponseDto(comment));
            }
            responseDtoList.add(new BulletinBoardResponseDto(bulletinBoard,commentList));
        }

        return responseDtoList;
    }

    public BulletinBoardResponseDto findOneBulletinBoard(Long id){
        List<BulletinBoard> bulletinBoardList = bulletinBoardRepository.findByIdOrderByModifiedAtDesc(id);
        List<CommentResponseDto> commentList= new ArrayList<>();
        for(BulletinBoard bulletinBoard : bulletinBoardList){
            for(Comment comment :bulletinBoard.getComments()){
                commentList.add(new CommentResponseDto(comment));
            }
        }


        BulletinBoard board =bulletinBoardRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("선택한 게시판은 존재하지 않습니다. 다시해주세요"));
        return new BulletinBoardResponseDto(board,commentList);
    }



    @Transactional
    public BulletinBoardResponseDto updateBulletinBoard(Long id , BulletinBoardRequestDto bulletinBoardRequestDto,HttpServletRequest request){
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
            BulletinBoard board=bulletinBoardRepository.findById(id).orElseThrow(()->
                    new IllegalArgumentException("선택한 게시판은 존재하지 않습니다. 다시해주세요"));

            if(user.getUsername().equals(board.getUsername())){
                board.update(bulletinBoardRequestDto);
                return new BulletinBoardResponseDto(board);
            }
            else {
                throw new IllegalArgumentException("작성자가 일치하지 않습니다.");
            }
        }
        else {
            return null;
        }
    }
    @Transactional
    public BulletinBoardResponseDto updateCompletedBulletinBoard(Long id, BulletinBoardRequestDto bulletinBoardrequestDto, HttpServletRequest request) {
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
            BulletinBoard board=bulletinBoardRepository.findById(id).orElseThrow(()->
                    new IllegalArgumentException("선택한 게시판은 존재하지 않습니다. 다시해주세요"));

            if(user.getUsername().equals(board.getUsername())){
                board.updateCompleted(bulletinBoardrequestDto);
                return new BulletinBoardResponseDto(board);
            }
            else {
                throw new IllegalArgumentException("작성자가 일치하지 않습니다.");
            }
        }
        else {
            return null;
        }
    }




//    public Long deleteBulletinBoard(Long id,String inputPsw) {
//        for(BulletinBoard bulletinBoard: bulletinBoardRepository.findByIdOrderByModifiedAtDesc(id)){
//            if(checkingPassword(bulletinBoard.getPassword(),inputPsw)){
//                BulletinBoard board = findBulletinBoard(id);
//                bulletinBoardRepository.delete(board);
//                return id;
//            }
//        }
//        return 0L;
//    }



}
