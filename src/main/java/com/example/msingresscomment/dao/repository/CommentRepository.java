package com.example.msingresscomment.dao.repository;


import com.example.msingresscomment.dao.entity.CommentEntity;
import com.example.msingresscomment.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
//    @Query(value = "SELECT id,status FROM CommentEntity WHERE id=:id and status=:status ")
//    Optional<CommentEntity> findByIdAndCommentStatus(Long id,Status status);
    Optional<CommentEntity> findByUserId(Long userId);
}
