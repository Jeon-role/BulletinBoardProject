package com.board.bulletinboardproject.repository;

import com.board.bulletinboardproject.entity.BulletinBoard;
import com.board.bulletinboardproject.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BulletinBoardRepository extends JpaRepository<BulletinBoard,Long> {

    List<BulletinBoard> findAllByOrderByModifiedAtDesc();
    List<BulletinBoard> findByIdOrderByModifiedAtDesc(Long id);
    List<BulletinBoard> findAllByUsername(String username);

//    Page<BulletinBoard>findAllByUser(User user, Pageable pageable);


}
