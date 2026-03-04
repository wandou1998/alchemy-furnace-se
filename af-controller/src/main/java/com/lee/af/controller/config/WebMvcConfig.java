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
 *
 * 在Spring 容器中，Config类本身及注册的Bean（如Filter、Interceptor）默认是单例
 * 标注@Configuration的Config类在Spring容器中仅存在一个实例，确保组件注册逻辑的一致性
 * 自定义的Filter、Interceptor通过@Component或@Bean声明为单例，避免重复创建对象，降低内存开销。
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private ApiLogInterceptor apiLogInterceptor;

    /** 注册过滤器，替换Request为ContentCachingRequestWrapper
     * 用于读取请求体，避免请求体只能读取一次的问题  工厂模式
     * 通过FilterRegistrationBean注册Filter
     * @return
     */

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
                /**
                 * 装饰器模式 --
                 * filter通过Wrapper（如ContentCachingRequestWrapper）包装原始HttpServletRequest,增强功能，
                 * 解决HttpServletRequest输入流只能读取一次的问题
                 */
                ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(httpRequest);
                /**
                 * 责任链模式 --
                 * Filter通过Filter链实现多过滤器的依次执行，每个Filter处理请求后，
                 * 通过filterChain.doFilter()将请求传递给下一个Filter
                 */
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
