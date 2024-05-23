package com.communify.global.config;

import com.communify.global.resolver.CurrentMemberIdArgumentResolver;
import com.communify.global.resolver.CurrentMemberNameArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final CurrentMemberIdArgumentResolver currentMemberIdArgumentResolver;
    private final CurrentMemberNameArgumentResolver currentMemberNameArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolverList) {
        resolverList.add(currentMemberIdArgumentResolver);
        resolverList.add(currentMemberNameArgumentResolver);
    }
}
