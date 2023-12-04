package com.board.bulletinboardproject.service;


import com.board.bulletinboardproject.dto.BulletinBoardRequestDto;
import com.board.bulletinboardproject.dto.BulletinBoardResponseDto;
import com.board.bulletinboardproject.dto.CommentResponseDto;
import com.board.bulletinboardproject.dto.StatusDto;
import com.board.bulletinboardproject.entity.BulletinBoard;
import com.board.bulletinboardproject.entity.Comment;
import com.board.bulletinboardproject.entity.User;
import com.board.bulletinboardproject.repositoryTest.BulletinBoardRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class BulletinBoardService {

    private final BulletinBoardRepository bulletinBoardRepository;


    public BulletinBoardService(BulletinBoardRepository bulletinBoardRepository) {
        this.bulletinBoardRepository = bulletinBoardRepository;

    }

    public BulletinBoardResponseDto createBulletinBoard(BulletinBoardRequestDto bulletinBoardRequestDto, User user) {
        BulletinBoard bulletinBoard = new BulletinBoard(bulletinBoardRequestDto, user);
        BulletinBoard saveBulletinBoard = bulletinBoardRepository.save(bulletinBoard);
        return new BulletinBoardResponseDto(saveBulletinBoard);
    }


    public List<BulletinBoardResponseDto> getBulletinBoards() {
//        String sortBy = "user_id";
//        boolean isAsc = false;
//        int page = 0;
//        int size = 20;
//        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
//        Sort sort = Sort.by(direction, sortBy);
//        Pageable pageable = PageRequest.of(page, size, sort);

        List<BulletinBoard> boardList = bulletinBoardRepository.findAllByOrderByModifiedAtDesc();
        List<BulletinBoardResponseDto> responseDtoList = new ArrayList<>();


        for (BulletinBoard bulletinBoard : boardList) {
            List<CommentResponseDto> commentList = new ArrayList<>();
            for (Comment comment : bulletinBoard.getComments()) {
                commentList.add(new CommentResponseDto(comment));
            }
            responseDtoList.add(new BulletinBoardResponseDto(bulletinBoard, commentList));
        }

        return responseDtoList;
    }

    public BulletinBoardResponseDto  findOneBulletinBoard(Long id) {
            List<CommentResponseDto> commentList = new ArrayList<>();
            for (Comment comment : findId(id).getComments()) {
                commentList.add(new CommentResponseDto(comment));
            }
            return new  BulletinBoardResponseDto(findId(id), commentList);
    }


    @Transactional
    public BulletinBoardResponseDto updateBulletinBoard(Long id, BulletinBoardRequestDto bulletinBoardRequestDto, User user) {
        if (user.getUsername().equals(findId(id).getUser().getUsername())) {

            findId(id).update(bulletinBoardRequestDto);
            return findOneBulletinBoard(findId(id).getId());
        } else {
            throw new IllegalArgumentException("작성자가 일치하지 않습니다.");
        }
    }

    @Transactional
    public BulletinBoardResponseDto updateCompletedBulletinBoard(Long id, BulletinBoardRequestDto bulletinBoardrequestDto, User user) {
        if (user.getUsername().equals(findId(id).getUser().getUsername())) {
            findId(id).updateCompleted(bulletinBoardrequestDto);
            return findOneBulletinBoard(findId(id).getId());
        } else {
            throw new IllegalArgumentException("작성자가 일치하지 않습니다.");
        }
    }


    public ResponseEntity<StatusDto> deleteBulletinBoard(Long id, User user) {
        if (user.getUsername().equals(findId(id).getUser().getUsername())) {
            bulletinBoardRepository.delete(findId(id));
            return ResponseEntity.ok(new StatusDto("삭제 성공", HttpStatus.OK.value()));
        } else {
            throw new IllegalArgumentException("작성자가 일치하지 않습니다.");
        }
    }

    public BulletinBoard findId(Long id){
        BulletinBoard board= bulletinBoardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시판이 존재하지 않아요"));
        return board;
    }


}
