package com.localsearch.global.config;

import com.localsearch.auth.AuthHandlerInterceptor;
import com.localsearch.auth.infrastructure.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final JwtProvider jwtProvider;

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowCredentials(false) // 쿠키,세션 허용 여부
                .exposedHeaders(HttpHeaders.LOCATION); // 리다이렉션 허용 여부
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthHandlerInterceptor(jwtProvider))
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/",
                        "/login",
                        "/auth/token",
                        "/sigh-up",
                        "/*.ico",
                        "/healthy/check",
                        "/error"
                ); // 핸들러가 실행되면 안되는 애들
    }
}
