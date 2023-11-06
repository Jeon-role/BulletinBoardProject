package com.board.bulletinboardproject.entity;

import com.board.bulletinboardproject.dto.BulletinBoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name="board")
public class BulletinBoard extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false )
    private String username;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String contents;
    @Column(nullable = false )
    private String password;

    public BulletinBoard(BulletinBoardRequestDto bulletinBoardRequestDto){
        this.username= bulletinBoardRequestDto.getUsername();
        this.title= bulletinBoardRequestDto.getTitle();
        this.contents= bulletinBoardRequestDto.getContents();
        this.password= bulletinBoardRequestDto.getPassword();
    }

    public void update(BulletinBoardRequestDto bulletinBoardRequestDto){
        this.username= bulletinBoardRequestDto.getUsername();
        this.title= bulletinBoardRequestDto.getTitle();
        this.contents= bulletinBoardRequestDto.getContents();
        this.password= bulletinBoardRequestDto.getPassword();
    }


}
