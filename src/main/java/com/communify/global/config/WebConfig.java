package com.communify.global.config;

import com.communify.global.resolver.MemberIdArgumentResolver;
import com.communify.global.resolver.MemberNameArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final MemberIdArgumentResolver memberIdArgumentResolver;
    private final MemberNameArgumentResolver memberNameArgumentResolver;

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolverList) {
        resolverList.add(memberIdArgumentResolver);
        resolverList.add(memberNameArgumentResolver);
    }
}
