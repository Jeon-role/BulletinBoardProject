package com.board.bulletinboardproject.service;


import com.board.bulletinboardproject.dto.BulletinBoardRequestDto;
import com.board.bulletinboardproject.dto.BulletinBoardResponseDto;
import com.board.bulletinboardproject.entity.BulletinBoard;
import com.board.bulletinboardproject.repository.BulletinBoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class BulletinBoardService {

    private final BulletinBoardRepository bulletinBoardRepository;

    public BulletinBoardService(BulletinBoardRepository bulletinBoardRepository){
        this.bulletinBoardRepository=bulletinBoardRepository;
    }

    public BulletinBoardResponseDto createBulletinBoard(BulletinBoardRequestDto bulletinBoardRequestDto){
        BulletinBoard bulletinBoard = new BulletinBoard(bulletinBoardRequestDto);

        BulletinBoard saveBulletinBoard = bulletinBoardRepository.save(bulletinBoard);
        BulletinBoardResponseDto bulletinBoardResponseDto = new BulletinBoardResponseDto(saveBulletinBoard);

        return bulletinBoardResponseDto;
    }

    public List<BulletinBoardResponseDto> getBulletinBoards(){
        return bulletinBoardRepository.findAllByOrderByModifiedAtDesc().stream().map(BulletinBoardResponseDto::new).toList();
    }

    public BulletinBoardResponseDto findOneBulletinBoard(Long id){
        BulletinBoard board =bulletinBoardRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("선택한 게시판은 존재하지 않습니다. 다시해주세요"));
        return new BulletinBoardResponseDto(board);
    }



    @Transactional
    public BulletinBoardResponseDto updateBulletinBoard(Long id , BulletinBoardRequestDto bulletinBoardRequestDto){
        BulletinBoard board= findBulletinBoard(id);
        for(BulletinBoard bulletinBoard: bulletinBoardRepository.findByIdOrderByModifiedAtDesc(id)){
            if(checkingPassword(bulletinBoard.getPassword(),bulletinBoardRequestDto.getPassword())){
                board.update(bulletinBoardRequestDto);
                return findOneBulletinBoard(id);
            }
        }

        return findOneBulletinBoard(id);
    }


    public Long deleteBulletinBoard(Long id,String inputPsw) {
        for(BulletinBoard bulletinBoard: bulletinBoardRepository.findByIdOrderByModifiedAtDesc(id)){
            if(checkingPassword(bulletinBoard.getPassword(),inputPsw)){
                BulletinBoard board = findBulletinBoard(id);
                bulletinBoardRepository.delete(board);
                return id;
            }
        }
        return 0L;
    }




    private BulletinBoard findBulletinBoard(Long id){
        return bulletinBoardRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("선택한 게시판은 존재하지 않습니다. 다시해주세요"));

    }


    public boolean checkingPassword(String nowPassword,String changePassword) {
        return nowPassword.equals(changePassword);
    }
}
