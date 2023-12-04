package com.board.bulletinboardproject.entity;

import com.board.bulletinboardproject.dto.CommentRequestDto;
import com.board.bulletinboardproject.dto.CommentResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name="comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private BulletinBoard board;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public Comment(CommentRequestDto commentRequestDto,BulletinBoard board,User user){
        this.comment=commentRequestDto.getComment();
        this.board=board;
        this.user=user;
    }
    public void update(CommentRequestDto commentRequestDto) {
        this.comment = commentRequestDto.getComment();
    }

}
