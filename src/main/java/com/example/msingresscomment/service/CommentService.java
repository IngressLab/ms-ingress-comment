package com.example.msingresscomment.service;

import com.example.msingresscomment.dao.entity.CommentEntity;
import com.example.msingresscomment.dao.repository.CommentRepository;
import com.example.msingresscomment.exception.NotFoundException;
import com.example.msingresscomment.mapper.CommentMapper;
import com.example.msingresscomment.model.enums.Status;
import com.example.msingresscomment.model.request.SaveCommentRequest;
import com.example.msingresscomment.model.request.UpdateCommentRequest;
import com.example.msingresscomment.model.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.msingresscomment.mapper.CommentMapper.*;
import static com.example.msingresscomment.model.enums.Status.DELETED;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;


    public CommentResponse getCommentById(Long id) {
        log.info("ActionLog.getComment.start id:{}", id);
        var comment = fetchCommentIfExits(id);
        log.info("ActionLog.getComment.end id:{}", id);
        return buildCommentResponse(comment);
    }

    public void saveComment(SaveCommentRequest request) {
        log.info("ActionLog.saveComment.start");
        commentRepository.save(buildCommentEntity(request));
        log.info("ActionLog.saveComment.end");
    }

    public void updateComment(Long userId, UpdateCommentRequest request) {
        log.info("ActionLog.updateComment.start userId:{}",userId);
        var comment = fetchCommentIfExitsByUserId(userId);
        updateCommentEntity(comment, request);
        commentRepository.save(comment);
        log.info("ActionLog.updateComment.end userId:{}",userId);
    }

    public void deleteComment(Long id) {
        log.info("ActionLog.deleteComment.start id:{}", id);
        var comment = fetchCommentIfExits(id);
        comment.setStatus(DELETED);
        commentRepository.save(comment);
        log.info("ActionLog.deleteComment.end id:{}", id);
    }

    public List<CommentResponse> getCommentsByProduct() {
        log.info("ActionLog.getCommentsByProduct.start");
        log.info("ActionLog.getCommentsByProduct.end");
        return commentRepository.findAll().stream().
                map(CommentMapper::getComments).collect(Collectors.toList());

    }

    private CommentEntity fetchCommentIfExits(Long id) {
        log.info("ActionLog.fetchCommentIfExits.start id:{}", id);
        log.info("ActionLog.fetchCommentIfExits.end id:{}", id);
        return commentRepository.findById(id).orElseThrow(
                ()->new NotFoundException("COMMENT_NOT_FOUND")
        );
    }
    private CommentEntity fetchCommentIfExitsByUserId(Long userId){
        log.info("ActionLog.fetchCommentIfExitsByUserId.start id:{}", userId);
        log.info("ActionLog.fetchCommentIfExitsByUserId.end id:{}", userId);
        return commentRepository.findByUserId(userId).orElseThrow(
                ()->new NotFoundException("COMMENT_NOT_FOUND")
        );
    }
}
