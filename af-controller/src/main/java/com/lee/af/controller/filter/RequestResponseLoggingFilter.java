//package com.lee.af.controller.filter;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import org.springframework.web.util.ContentCachingRequestWrapper;
//import org.springframework.web.util.ContentCachingResponseWrapper;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//@Slf4j
//@Component
//public class RequestResponseLoggingFilter extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        // 包装请求和响应，以便可以多次读取 Body
//        ContentCachingRequestWrapper wrappingRequest = new ContentCachingRequestWrapper(request, 0);
//        ContentCachingResponseWrapper wrappingResponse = new ContentCachingResponseWrapper(response);
//
//        try {
//            filterChain.doFilter(wrappingRequest, wrappingResponse);
//        } finally {
//            // 请求处理完成后，记录日志
//
//            // 1. 记录请求 Body (仅 JSON/Text)
//            byte[] content = wrappingRequest.getContentAsByteArray();
//            if (content.length > 0) {
//                String requestBody = new String(content, StandardCharsets.UTF_8);
//                log.info("Request Body: {}", requestBody);
//            }
//
//            // 2. 记录响应结果
//            byte[] responseContent = wrappingResponse.getContentAsByteArray();
//            if (responseContent.length > 0) {
//                String responseBody = new String(responseContent, StandardCharsets.UTF_8);
//                log.info("Response Body: {}", responseBody);
//            }
//
//            // 重要：将响应内容写回原始输出流，否则客户端收不到数据
//            wrappingResponse.copyBodyToResponse();
//        }
//
//    }
//}
