package com.lee.af.controller.config;

import com.lee.af.controller.interceptor.ApiLogInterceptor;
import com.lee.af.controller.wrapper.ContentCachingRequestWrapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;

/**
 * WebMvcConfig配置类
 * 实现WebMvcConfigurer接口，用于配置Spring MVC的拦截器和过滤器
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private ApiLogInterceptor apiLogInterceptor;

    // 注册过滤器，替换Request为ContentCachingRequestWrapper
    @Bean
    public FilterRegistrationBean<ContentCachingFilter> contentCachingFilter() {
        FilterRegistrationBean<ContentCachingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ContentCachingFilter());
        registrationBean.addUrlPatterns("/*"); // 对所有请求生效
        return registrationBean;
    }

    // 自定义过滤器
    public static class ContentCachingFilter implements Filter {
        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {
            // 仅对HTTP请求生效 替换Request为ContentCachingRequestWrapper
            if (request instanceof HttpServletRequest httpRequest) {
                ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(httpRequest);
                chain.doFilter(wrappedRequest, response);
            } else {
                chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiLogInterceptor)
                .addPathPatterns("/**") // 拦截所有路径
                .excludePathPatterns("/static/**", "/error", "/favicon.ico"); // 排除静态资源和错误页面
    }
}
