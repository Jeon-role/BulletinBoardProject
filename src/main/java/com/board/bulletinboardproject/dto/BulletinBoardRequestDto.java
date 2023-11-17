package com.board.bulletinboardproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BulletinBoardRequestDto {
    private String username;
    private String title;
    private String contents;
    private String password;
    private Boolean completed;
}
