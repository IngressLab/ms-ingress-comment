package com.example.msingresscomment.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE,makeFinal = true)
public enum ExceptionMessage {
    UNEXPECTED_ERROR("Unexpected error occurred"),
    COMMENT_NOT_FOUND("Comment not found");

    private final String message;
}
