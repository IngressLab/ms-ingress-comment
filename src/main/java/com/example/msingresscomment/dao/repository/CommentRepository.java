package com.example.msingresscomment.dao.repository;
import com.example.msingresscomment.dao.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Optional<CommentEntity> findByIdAndUserId(Long commentId,Long userId);
}
