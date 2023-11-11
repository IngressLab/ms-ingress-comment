package com.example.msingresscomment.mapper;

import com.example.msingresscomment.dao.entity.CommentEntity;
import com.example.msingresscomment.model.constants.HeaderConstants;
import com.example.msingresscomment.model.enums.Status;
import com.example.msingresscomment.model.request.SaveCommentRequest;
import com.example.msingresscomment.model.request.UpdateCommentRequest;
import com.example.msingresscomment.model.response.CommentResponse;

import java.time.LocalDateTime;

public class CommentMapper {
    public static CommentResponse buildCommentResponse(CommentEntity commentEntity) {
        return CommentResponse.builder()
                .productId(commentEntity.getProductId())
                .text(commentEntity.getText())
                .userId(commentEntity.getUserId())
                .build();
    }
    public static CommentEntity buildCommentEntity(SaveCommentRequest request){
     return CommentEntity.builder()
             .userId(request.getUserId())
             .productId(request.getProductId())
             .text(request.getText())
             .status(Status.ACTIVE)
             .build();
    }
    public static void updateCommentEntity(CommentEntity commentEntity, UpdateCommentRequest commentRequest){
      commentEntity.setText(commentRequest.getText());
    }
    public static CommentResponse getComments(CommentEntity entity){
      return CommentResponse.builder()
              .userId(entity.getUserId())
              .productId(entity.getProductId())
              .text(entity.getText())
              .build();
    }
}
