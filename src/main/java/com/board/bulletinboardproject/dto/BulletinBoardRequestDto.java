package com.board.bulletinboardproject.dto;

import lombok.Getter;

@Getter
public class BulletinBoardRequestDto {
    private String username;
    private String title;
    private String contents;
    private String password;
}
