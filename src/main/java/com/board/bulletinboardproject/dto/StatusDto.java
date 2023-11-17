package com.board.bulletinboardproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusDto {
    private String msg;
    private String statusCode;

    public StatusDto(String msg,String statusCode){
        this.msg=msg;
        this.statusCode=statusCode;
    }
}
