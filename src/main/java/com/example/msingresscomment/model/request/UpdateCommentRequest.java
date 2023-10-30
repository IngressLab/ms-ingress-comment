package com.example.msingresscomment.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCommentRequest {
    private Long id;
    private Long userId;
    private String text;

    private LocalDateTime createdAt;

}
