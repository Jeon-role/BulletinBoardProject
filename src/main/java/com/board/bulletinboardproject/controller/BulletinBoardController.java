package com.board.bulletinboardproject.controller;


import com.board.bulletinboardproject.dto.BulletinBoardRequestDto;
import com.board.bulletinboardproject.dto.BulletinBoardResponseDto;
import com.board.bulletinboardproject.dto.StatusDto;
import com.board.bulletinboardproject.service.BulletinBoardService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BulletinBoardController {

    private final BulletinBoardService bulletinBoardService;

    public BulletinBoardController(BulletinBoardService bulletinBoardService) {

        this.bulletinBoardService=bulletinBoardService;
    }

    @PostMapping("/boards")
    public BulletinBoardResponseDto createBulletinBoard(@RequestBody BulletinBoardRequestDto bulletinBoardRequestDto, HttpServletRequest request) {

        return bulletinBoardService.createBulletinBoard(bulletinBoardRequestDto,request);
    }


    @GetMapping("/boards")
    public List<BulletinBoardResponseDto> getBulletinBoard(){
        return bulletinBoardService.getBulletinBoards();
    }

    @GetMapping("/boards/{id}")
    public BulletinBoardResponseDto findBulletinOneBoard(@PathVariable Long id){
        return bulletinBoardService.findOneBulletinBoard(id);
    }


    @PutMapping("/boards/{id}")
    public BulletinBoardResponseDto updateBulletinBoard(@PathVariable Long id, @RequestBody BulletinBoardRequestDto bulletinBoardrequestDto ,HttpServletRequest request) {
        return bulletinBoardService.updateBulletinBoard(id, bulletinBoardrequestDto,request);
    }

    @PutMapping("/boards/completed/{id}")
    public BulletinBoardResponseDto updateCompletedBulletinBoard(@PathVariable Long id, @RequestBody BulletinBoardRequestDto bulletinBoardrequestDto ,HttpServletRequest request) {
        return bulletinBoardService.updateCompletedBulletinBoard(id, bulletinBoardrequestDto,request);
    }


    @DeleteMapping("/boards/{id}")
    public ResponseEntity<StatusDto> deleteBulletinBoard(@PathVariable Long id ,HttpServletRequest request) {
        return bulletinBoardService.deleteBulletinBoard(id,request);
    }



}
