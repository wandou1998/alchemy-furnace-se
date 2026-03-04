package com.lee.af.controller.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Enumeration;

/**
 * API日志拦截器
 * 用于记录HTTP请求的详细信息，包括请求参数、响应状态和执行时间等
 */
@Slf4j
@Component
public class ApiLogInterceptor implements HandlerInterceptor {

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
        /** 如果请求是 POST/PUT 且 Content-Type 为 application/json，
         * 参数在 Request Body 中。由于 InputStream 只能读取一次，
         * 如果在拦截器中读取了 Body，Controller 层就无法再读取，会导致报错。
         **/
        String method = request.getMethod();
        if ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method)) {
//            Object[] Parameters = ((HandlerMethod) handler).getMethodParameters();
        }

//        // 1. 获取 Content-Type
//        String contentType = request.getContentType();
//
//        // 2. 判断是否为 JSON 请求
//        // 忽略大小写，并处理 contentType 为 null 的情况
//        boolean isJson = contentType != null && contentType.contains("application/json");
//
//        if (!isJson) {
//            Enumeration<String> parameterNames = request.getParameterNames();
//            if (parameterNames.hasMoreElements()) {
//                StringBuilder params = new StringBuilder();
//                while (parameterNames.hasMoreElements()) {
//                    String paramName = parameterNames.nextElement();
//                    params.append(paramName).append("=").append(request.getParameter(paramName)).append("&");
//                }
//                // 去掉最后一个&
//                if (params.length() > 0) {
//                    params.deleteCharAt(params.length() - 1);
//                    log.info("Request Params : {}", params);
//                }
//            } else {
//                log.info("Request Params : (None)");
//            }
//        } else {
//            // 如果是 JSON 请求，打印提示，告知参数在 Body 中
//            log.info("Request Params : (Skipped, see Request Body)");
//        }
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
        log.info("开始打印返回结果==");
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
                log.info("Request Params : {}", params);
            }
        } else {
            log.info("Request Params : (None)");
        }
        log.info("Response Status: {}", response.getStatus());
        log.info("Time Consuming : {} ms", executeTime);

        // 如果有异常，打印异常堆栈（注意：全局异常处理器通常也会处理，这里仅作记录）
        if (ex != null) {
            log.error("Exception Occurred: ", ex);
        }

        log.info("=========================================== End ===========================================");
    }
}
