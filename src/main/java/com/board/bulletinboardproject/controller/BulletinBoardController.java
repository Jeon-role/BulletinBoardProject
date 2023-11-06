package com.board.bulletinboardproject.controller;


import com.board.bulletinboardproject.dto.BulletinBoardRequestDto;
import com.board.bulletinboardproject.dto.BulletinBoardResponseDto;
import com.board.bulletinboardproject.service.BulletinBoardService;
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
    public BulletinBoardResponseDto createBulletinBoard(@RequestBody BulletinBoardRequestDto bulletinBoardRequestDto) {

        return bulletinBoardService.createBulletinBoard(bulletinBoardRequestDto);
    }

//    @GetMapping("/boards")
//    public BulletinBoardResponseDto createBulletinBoard(@RequestBody BulletinBoardRequestDto bulletinBoardRequestDto) {
//
//        return bulletinBoardService.createBulletinBoard(bulletinBoardRequestDto);
//    }

    @GetMapping("/boards")
    public List<BulletinBoardResponseDto> getBulletinBoard(){
        return bulletinBoardService.getBulletinBoards();
    }

    @GetMapping("/boards/{id}")
    public BulletinBoardResponseDto findBulletinBoard(@PathVariable Long id){
        return bulletinBoardService.findOneBulletinBoard(id);
    }


    @PutMapping("/boards/{id}")
    public BulletinBoardResponseDto updateBulletinBoard(@PathVariable Long id, @RequestBody BulletinBoardRequestDto bulletinBoardrequestDto) {
        return bulletinBoardService.updateBulletinBoard(id, bulletinBoardrequestDto);
    }


    @DeleteMapping("/boards/{id}/{inputPsw}")
    public Long deleteBulletinBoard(@PathVariable Long id ,@PathVariable String inputPsw) {
        return bulletinBoardService.deleteBulletinBoard(id,inputPsw);
    }



}
