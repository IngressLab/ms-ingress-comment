package com.example.msingresscomment.controller;

import com.example.msingresscomment.model.request.SaveCommentRequest;
import com.example.msingresscomment.model.request.UpdateCommentRequest;
import com.example.msingresscomment.model.response.CommentResponse;
import com.example.msingresscomment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService service;

    @GetMapping("/{id}")
    public CommentResponse getCommentById(@PathVariable Long id) {
        return service.getCommentById(id);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void saveComment(@RequestBody SaveCommentRequest request) {
        service.saveComment(request);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(NO_CONTENT)
    public void updateComment(@PathVariable Long userId, @RequestBody UpdateCommentRequest request) {
        service.updateComment(userId, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteComment(@PathVariable Long id) {
        service.deleteComment(id);
    }

    @GetMapping
    public List<CommentResponse> getComments() {
        return service.getCommentsByProduct();
    }


}
