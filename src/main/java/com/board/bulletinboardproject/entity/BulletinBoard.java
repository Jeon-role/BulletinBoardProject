package com.board.bulletinboardproject.entity;

import com.board.bulletinboardproject.dto.BulletinBoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name="board")
public class BulletinBoard extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String contents;


    @Column
    private String username;

    @Column(nullable = false)
    private Boolean completed=false;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();


    public BulletinBoard(BulletinBoardRequestDto bulletinBoardRequestDto){
        this.title= bulletinBoardRequestDto.getTitle();
        this.contents= bulletinBoardRequestDto.getContents();
        this.username=bulletinBoardRequestDto.getUsername();
    }


    public void update(BulletinBoardRequestDto bulletinBoardRequestDto){
        this.title= bulletinBoardRequestDto.getTitle();
        this.contents= bulletinBoardRequestDto.getContents();
    }

    public void updateCompleted(BulletinBoardRequestDto bulletinBoardRequestDto){
        this.completed=bulletinBoardRequestDto.getCompleted();
    }


}
