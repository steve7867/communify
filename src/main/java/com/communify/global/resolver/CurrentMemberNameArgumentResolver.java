package com.communify.global.resolver;

import com.communify.domain.auth.annotation.MemberName;
import com.communify.domain.auth.annotation.LoginCheck;
import com.communify.global.application.SessionService;
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
public class CurrentMemberNameArgumentResolver implements HandlerMethodArgumentResolver {

    private final SessionService sessionService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasMethodAnnotation(LoginCheck.class)
                && parameter.hasParameterAnnotation(MemberName.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {

        return sessionService.get(SessionKey.MEMBER_NAME).get();
    }
}
