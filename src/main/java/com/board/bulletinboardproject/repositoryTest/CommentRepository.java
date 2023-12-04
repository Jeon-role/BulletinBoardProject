package com.board.bulletinboardproject.repositoryTest;

import com.board.bulletinboardproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Optional<Comment> findByComment(String comment);
}
