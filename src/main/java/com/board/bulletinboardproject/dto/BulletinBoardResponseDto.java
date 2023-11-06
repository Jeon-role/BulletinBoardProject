package com.board.bulletinboardproject.dto;

import com.board.bulletinboardproject.entity.BulletinBoard;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BulletinBoardResponseDto {

    private String username;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public BulletinBoardResponseDto(BulletinBoard bulletinBoard){

        this.username=bulletinBoard.getUsername();
        this.title=bulletinBoard.getTitle();
        this.contents=bulletinBoard.getContents();
        this.createdAt=bulletinBoard.getCreatedAt();
        this.modifiedAt=bulletinBoard.getModifiedAt();
    }



}
