package com.board.bulletinboardproject.controller;


import com.board.bulletinboardproject.dto.BulletinBoardRequestDto;
import com.board.bulletinboardproject.dto.BulletinBoardResponseDto;
import com.board.bulletinboardproject.dto.StatusDto;
import com.board.bulletinboardproject.jwt.UserDetailsImpl;
import com.board.bulletinboardproject.service.BulletinBoardService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<BulletinBoardResponseDto> createBulletinBoard(@RequestBody BulletinBoardRequestDto bulletinBoardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BulletinBoardResponseDto bulletinBoardResponseDto = bulletinBoardService.createBulletinBoard(bulletinBoardRequestDto,userDetails.getUser());
        return ResponseEntity.status(201).body(bulletinBoardResponseDto);
    }


    @GetMapping("/boards")
    public ResponseEntity<List<BulletinBoardResponseDto>> getBulletinBoard(){
        return ResponseEntity.ok().body(bulletinBoardService.getBulletinBoards());
    }

    @GetMapping("/boards/{id}")
    public ResponseEntity<StatusDto> findBulletinOneBoard(@PathVariable Long id){
        try {
            BulletinBoardResponseDto bulletinBoardResponseDto = bulletinBoardService.findOneBulletinBoard(id);
            return ResponseEntity.ok().body(bulletinBoardResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new StatusDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }


    @PutMapping("/boards/{id}")
    public  ResponseEntity<StatusDto> updateBulletinBoard(@PathVariable Long id, @RequestBody BulletinBoardRequestDto bulletinBoardrequestDto,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            BulletinBoardResponseDto bulletinBoardResponseDto = bulletinBoardService.updateBulletinBoard(id, bulletinBoardrequestDto,userDetails.getUser());
            return ResponseEntity.ok().body(bulletinBoardResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new StatusDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PutMapping("/boards/completed/{id}")
    public ResponseEntity<StatusDto> updateCompletedBulletinBoard(@PathVariable Long id, @RequestBody BulletinBoardRequestDto bulletinBoardrequestDto ,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            BulletinBoardResponseDto bulletinBoardResponseDto = bulletinBoardService.updateCompletedBulletinBoard(id, bulletinBoardrequestDto,userDetails.getUser());
            return ResponseEntity.ok().body(bulletinBoardResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new StatusDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }


    @DeleteMapping("/boards/{id}")
    public ResponseEntity<StatusDto> deleteBulletinBoard(@PathVariable Long id,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return bulletinBoardService.deleteBulletinBoard(id,userDetails.getUser());
    }



}
