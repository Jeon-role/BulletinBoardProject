package com.board.bulletinboardproject.dto;


import com.board.bulletinboardproject.entity.BulletinBoard;
import com.board.bulletinboardproject.entity.Comment;
import com.board.bulletinboardproject.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDto extends StatusDto {
    private String comment;
    private String username;


    public CommentResponseDto(Comment comment){
        this.comment=comment.getComment();
        this.username=comment.getUser().getUsername();
    }
}
