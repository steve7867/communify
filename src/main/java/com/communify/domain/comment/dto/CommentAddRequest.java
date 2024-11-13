package com.communify.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class CommentAddRequest {

    @NotBlank
    @Size(min = 1, max = 200)
    private final String content;
}
