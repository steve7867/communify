package com.communify.domain.user;

import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.auth.annotation.NotLoginCheck;
import com.communify.domain.auth.annotation.UserId;
import com.communify.domain.certification.CertificationService;
import com.communify.domain.certification.exception.NotCertifiedException;
import com.communify.domain.user.dto.PasswordUpdateForm;
import com.communify.domain.user.dto.UserInfoForSearch;
import com.communify.domain.user.dto.UserSignUpForm;
import com.communify.domain.user.dto.UserWithdrawRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CertificationService certificationService;

    @PostMapping
    @ResponseStatus(CREATED)
    @NotLoginCheck
    public void signUp(@RequestBody @Valid UserSignUpForm form) {
        boolean certified = certificationService.isCertified();
        if (!certified) {
            throw new NotCertifiedException(form.getEmail());
        }

        String email = form.getEmail();
        String password = form.getPassword();
        String name = form.getName();

        userService.signUp(email, password, name);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(OK)
    @LoginCheck
    public UserInfoForSearch viewUserInfo(@PathVariable @NotNull @Positive Long userId,
                                          @UserId Long searcherId) {

        return userService.getUserInfo(userId, searcherId);
    }

    @DeleteMapping("/me")
    @ResponseStatus(OK)
    @LoginCheck
    public void withdraw(@RequestBody @Valid UserWithdrawRequest request,
                         @UserId Long userId) {

        String password = request.getPassword();
        userService.withdraw(password, userId);
    }

    @PatchMapping("/me/password")
    @ResponseStatus(OK)
    @LoginCheck
    public void updatePassword(@RequestBody @Valid PasswordUpdateForm form,
                               @UserId Long userId) {

        String currentPassword = form.getCurrentPassword();
        String newPassword = form.getNewPassword();

        userService.updatePassword(currentPassword, newPassword, userId);
    }

    @PostMapping("/me/token")
    @ResponseStatus(OK)
    @LoginCheck
    public void setToken(@RequestBody @NotBlank String token,
                         @UserId Long userId) {

        userService.setToken(token, userId);
    }
}
