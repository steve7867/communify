package com.communify.global.resolver;

import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.domain.auth.annotation.MemberId;
import com.communify.domain.auth.error.exception.NotLoggedInException;
import com.communify.global.application.session.SessionService;
import com.communify.global.util.SessionKey;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class MemberIdArgumentResolver implements HandlerMethodArgumentResolver {

    private final SessionService sessionService;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasMethodAnnotation(LoginCheck.class)
                && parameter.hasParameterAnnotation(MemberId.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter,
                                  final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest,
                                  final WebDataBinderFactory binderFactory) {

        return sessionService.get(SessionKey.MEMBER_ID)
                .orElseThrow(NotLoggedInException::new);
    }
}
