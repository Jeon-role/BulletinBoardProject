package com.board.bulletinboardproject.repositoryTest;

import com.board.bulletinboardproject.entity.BulletinBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BulletinBoardRepository extends JpaRepository<BulletinBoard,Long> {

    List<BulletinBoard> findAllByOrderByModifiedAtDesc();


    Optional<BulletinBoard> findByTitle(String title);
}
