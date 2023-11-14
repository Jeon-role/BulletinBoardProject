package com.board.bulletinboardproject.dto;



import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class MsgStatusDto {
    private String msg;
    private String statusCode;

    public MsgStatusDto(String msg, String statusCode){
        this.msg=msg;
        this.statusCode=statusCode;
    }
}
