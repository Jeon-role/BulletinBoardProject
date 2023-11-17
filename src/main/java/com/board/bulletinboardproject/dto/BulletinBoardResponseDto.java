package com.board.bulletinboardproject.dto;

import com.board.bulletinboardproject.entity.BulletinBoard;
import com.board.bulletinboardproject.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BulletinBoardResponseDto {

    private String username;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Boolean completed;
    private List<CommentResponseDto> commentList = new ArrayList<>();

    public BulletinBoardResponseDto(BulletinBoard bulletinBoard){

        this.username=bulletinBoard.getUsername();
        this.title=bulletinBoard.getTitle();
        this.contents=bulletinBoard.getContents();
        this.createdAt=bulletinBoard.getCreatedAt();
        this.modifiedAt=bulletinBoard.getModifiedAt();
        this.completed=bulletinBoard.getCompleted();
    }

    public BulletinBoardResponseDto(BulletinBoard bulletinBoard,List<CommentResponseDto> commentList){
        this.username=bulletinBoard.getUsername();
        this.title=bulletinBoard.getTitle();
        this.contents=bulletinBoard.getContents();
        this.createdAt=bulletinBoard.getCreatedAt();
        this.modifiedAt=bulletinBoard.getModifiedAt();
        this.completed=bulletinBoard.getCompleted();
        this.commentList = commentList;
    }



}
