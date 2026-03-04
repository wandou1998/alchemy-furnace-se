package com.lee.af.controller.interceptor;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lee.af.controller.wrapper.ContentCachingRequestWrapper;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Enumeration;

/**
 * API日志拦截器
 * 用于记录HTTP请求的详细信息，包括请求参数、响应状态和执行时间等
 * -- 代理模式 interceptor基于**AOP（面向切面编程）**实现，通过动态代理（JDK动态代理或CGLIB）在Controller方法执行前后插入拦截逻辑
 * -- 模板方法模式  Interceptor的preHandle（执行前）、postHandle（执行后）、afterCompletion（完成后）方法构成固定流程骨架，用户只需重写这些方法实现具体逻辑
 */
@Slf4j
@Component
public class ApiLogInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String START_TIME_ATTRIBUTE_NAME = "startTime";

    /**
     * 控制器方法调用之前
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 记录请求开始时间
        long startTime = System.currentTimeMillis();
        request.setAttribute(START_TIME_ATTRIBUTE_NAME, startTime);


        // 打印请求信息
        log.info("========================================== Start ==========================================");
        log.info("开始记录请求：URL : {}, HTTP Method : {}, IP : {}, Class Method  : {}", request.getRequestURL(),request.getMethod(), request.getRemoteAddr(), handler);


        // 打印请求参数
        // 转换为自定义的RequestWrapper
        ContentCachingRequestWrapper wrappedRequest = (ContentCachingRequestWrapper) request;
        String requestParameters = "";
        String contentType = request.getContentType();
        // 判断是否为 JSON 请求
        boolean isJson = contentType != null && contentType.contains("application/json");
        /** 如果请求是 POST/PUT 且 Content-Type 为 application/json，
         * 参数在 Request Body 中。由于 InputStream 只能读取一次，
         * 如果在拦截器中读取了 Body，Controller 层就无法再读取，会导致报错。
         **/
        String method = request.getMethod();
        if (isJson) {
            String requestBody = wrappedRequest.getBody(); //使用重写的ContentCachingRequestWrapper读取请求体 可多次读取
            Object jsonParams  = objectMapper.readValue(requestBody, Object.class);  //读取参数
            log.info("Request Params : {}", jsonParams );
        } else {
            if  (contentType != null && contentType.contains("multipart/form-data")) {
                log.info("过滤文件上传参数，不打印...");
            } else {
                Enumeration<String> parameterNames = request.getParameterNames();
                if (parameterNames.hasMoreElements()) {
                    StringBuilder params = new StringBuilder();
                    while (parameterNames.hasMoreElements()) {
                        String paramName = parameterNames.nextElement();
                        params.append(paramName).append("=").append(request.getParameter(paramName)).append("&");
                    }
                    // 去掉最后一个&
                    if (params.length() > 0) {
                        params.deleteCharAt(params.length() - 1);
                        requestParameters = params.toString();
                    }
                }
                log.info("Request Params : {}", requestParameters);
            }
        }
        return true;
    }

    /**
     * 控制器方法调用之后，视图渲染之前
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 这里可以处理 modelAndView，如果只是记录日志，通常留空
    }

    /**
     * 请求处理完成之后（通常用于记录总耗时和响应状态）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 获取开始时间
        Long startTime = (Long) request.getAttribute(START_TIME_ATTRIBUTE_NAME);
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;

        // 打印响应状态和耗时
        //这里只能打印get请求参数 不能打印post请求参数
        log.info("开始打印返回结果==========");
        log.info("Response Status: {}", response.getStatus());
        log.info("Time Consuming : {} ms", executeTime);

        // 如果有异常，打印异常堆栈（注意：全局异常处理器通常也会处理，这里仅作记录）
        if (ex != null) {
            log.error("Exception Occurred: ", ex);
        }

        log.info("=========================================== End ===========================================");
    }

}
