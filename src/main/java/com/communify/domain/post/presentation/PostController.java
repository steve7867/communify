package com.communify.domain.post.presentation;

import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.auth.annotation.MemberId;
import com.communify.domain.auth.annotation.MemberName;
import com.communify.domain.post.application.PostDeleteService;
import com.communify.domain.post.application.PostEditService;
import com.communify.domain.post.application.PostSearchService;
import com.communify.domain.post.application.PostUploadService;
import com.communify.domain.post.dto.PostDeleteRequest;
import com.communify.domain.post.dto.PostEditRequest;
import com.communify.domain.post.dto.PostUploadRequest;
import com.communify.domain.post.dto.incoming.PostOutlineSearchCondition;
import com.communify.domain.post.dto.incoming.PostUploadForm;
import com.communify.domain.post.dto.outgoing.PostDetail;
import com.communify.domain.post.dto.outgoing.PostOutline;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostUploadService postUploadService;
    private final PostSearchService postSearchService;
    private final PostEditService postEditService;
    private final PostDeleteService postDeleteService;
    private final PostUploadFormValidator postUploadFormValidator;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(CREATED)
    @LoginCheck
    public void upload(@ModelAttribute @Valid PostUploadForm form,
                       @MemberId Long memberId,
                       @MemberName String memberName) {

        PostUploadRequest request = new PostUploadRequest(form.getTitle(),
                form.getContent(),
                Collections.unmodifiableList(form.getFileList()),
                form.getCategoryId(),
                memberId,
                memberName);

        postUploadService.uploadPost(request);
    }

    /*
     * @InitBinder는 해당 컨트롤러로 들어오는 모든 요청에 대해 추가적인 설정을 할 때 사용한다.
     * 모든 요청에 대해 본래 메서드 호출 전에 @InitBinder 메서드를 먼저 호출한다.
     * 특정 객체에만 적용하고자 할 때는 @InitBinder("postUploadForm")와 같이 사용한다.
     * 그럼, PostUploadForm 객체에 대해서만 @InitBinder를 호출한다.
     * WebDataBinder에 검증기를 추가하면 validation을 자동으로 적용할 수 있다.
     */
    @InitBinder("postUploadForm")
    public void addPostUploadFormValidator(WebDataBinder dataBinder) {
        dataBinder.addValidators(postUploadFormValidator);
    }

    @GetMapping
    @ResponseStatus(OK)
    @LoginCheck
    public List<PostOutline> getPostOutlines(@ModelAttribute @Valid PostOutlineSearchCondition searchCond) {
        return postSearchService.getPostOutlineList(searchCond);
    }

    @GetMapping("/{postId}")
    @LoginCheck
    public ResponseEntity<PostDetail> getPostDetail(@PathVariable Long postId,
                                                    @MemberId Long memberId) {

        Optional<PostDetail> postDetailOpt = postSearchService.getPostDetail(postId);
        if (postDetailOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        postEditService.incrementView(postId, memberId);

        return ResponseEntity.ok(postDetailOpt.get());
    }

    @PatchMapping(value = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(OK)
    @LoginCheck
    public void edit(@PathVariable Long postId,
                     @ModelAttribute @Valid PostUploadForm form,
                     @MemberId Long memberId) {

        PostEditRequest request = new PostEditRequest(postId, form.getTitle(),
                form.getContent(),
                Collections.unmodifiableList(form.getFileList()),
                form.getCategoryId(),
                memberId);

        postEditService.editPost(request);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(OK)
    @LoginCheck
    public void delete(@PathVariable Long postId,
                       @MemberId Long memberId) {

        PostDeleteRequest request = new PostDeleteRequest(postId, memberId);

        postDeleteService.deletePost(request);
    }
}
