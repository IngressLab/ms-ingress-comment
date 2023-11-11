package com.example.msingresscomment.model.request;

import com.example.msingresscomment.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveCommentRequest {
    private Long userId;
    private Long productId;
    private String text;

}
