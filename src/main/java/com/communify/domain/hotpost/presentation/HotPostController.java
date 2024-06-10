package com.communify.domain.hotpost.presentation;

import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.hotpost.application.HotPostSearchService;
import com.communify.domain.post.dto.outgoing.PostOutline;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class HotPostController {

    private final HotPostSearchService hotPostSearchService;

    @GetMapping("/hot")
    @ResponseStatus(OK)
    @LoginCheck
    public List<PostOutline> getHotPostOutlines() {
        return hotPostSearchService.getHotPostOutlineList();
    }
}
