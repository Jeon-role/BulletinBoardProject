package com.board.bulletinboardproject.repository;

import com.board.bulletinboardproject.entity.BulletinBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BulletinBoardRepository extends JpaRepository<BulletinBoard,Long> {

    List<BulletinBoard> findAllByOrderByModifiedAtDesc();
    List<BulletinBoard> findByIdOrderByModifiedAtDesc(Long id);



}
