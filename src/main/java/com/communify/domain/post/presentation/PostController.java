package com.communify.domain.post.presentation;

import com.communify.domain.auth.annotation.CurrentMemberId;
import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.post.application.PostService;
import com.communify.domain.post.dto.PostDetail;
import com.communify.domain.post.dto.PostOutline;
import com.communify.domain.post.dto.PostSearchCondition;
import com.communify.domain.post.dto.PostUploadRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostUploadRequestValidator postUploadRequestValidator;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(CREATED)
    @LoginCheck
    public void upload(@ModelAttribute @Valid PostUploadRequest request,
                       @CurrentMemberId Long memberId) {

        postService.uploadPost(request, memberId);
    }

    /*
     * @InitBinder는 해당 컨트롤러로 들어오는 모든 요청에 대해 추가적인 설정을 할 때 사용한다.
     * 모든 요청에 대해 본래 메서드 호출 전에 @InitBinder 메서드를 먼저 호출한다.
     * 특정 객체에만 적용하고자 할 때는 @InitBinder("PostUploadRequest")와 같이 사용한다.
     * 그럼, PostUploadRequest 객체에 대해서만 @InitBinder를 호출한다.
     * WebDataBinder에 검증기를 추가하면 validation을 자동으로 적용할 수 있다.
     */
    @InitBinder("PostUploadRequest")
    public void addPostUploadRequestValidator(WebDataBinder dataBinder) {
        dataBinder.addValidators(postUploadRequestValidator);
    }

    @GetMapping
    @ResponseStatus(OK)
    @LoginCheck
    public List<PostOutline> getPosts(@ModelAttribute @Valid PostSearchCondition searchCond) {
        return postService.getPostOutlineList(searchCond);
    }

    @GetMapping("/{postId}")
    @ResponseStatus(OK)
    @LoginCheck
    public PostDetail getPostDetail(@PathVariable Long postId,
                                    @CurrentMemberId Long memberId) {

        PostDetail postDetail = postService.getPostDetail(postId);
//        postService.incrementView(postId, memberId); //todo: 조회 수 카운트 추가
        return postDetail;
    }

    @PatchMapping(value = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(OK)
    @LoginCheck
    public void edit(@PathVariable Long postId,
                     @ModelAttribute @Valid PostUploadRequest request,
                     @CurrentMemberId Long memberId) {

        postService.editPost(postId, request, memberId);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(OK)
    @LoginCheck
    public void delete(@PathVariable Long postId,
                       @CurrentMemberId Long memberId) {

        postService.deletePost(postId, memberId);
    }
}
