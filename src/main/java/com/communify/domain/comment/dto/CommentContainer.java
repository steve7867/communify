package com.communify.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class CommentContainer {

    @NotBlank
    @Size(max = 200, message = "댓글은 최대 {0}자까지 가능합니다.")
    private final String content;
}
