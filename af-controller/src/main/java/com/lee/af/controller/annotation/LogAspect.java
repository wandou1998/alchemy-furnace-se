package com.lee.af.controller.annotation;


import com.alibaba.fastjson2.JSON;
import com.lee.af.entity.OperLog;
import com.lee.af.utils.IpUtils;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@Aspect
@Component
public class LogAspect {

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);
   //未建表 注释引用
//    @Autowired
//    private OperLogMapper operLogMapper;

    // ！！！！！ 配置织入点 ！！！！！Pointcut
    @Pointcut("@annotation(com.lee.af.controller.annotation.Log)")
    public void logPointCut() {}

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        // 获取当前请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        OperLog operLog = new OperLog();
        operLog.setStatus(0); // 默认正常
        operLog.setOperTime(LocalDateTime.now());

        try {
            // 1. 执行业务逻辑 如果不调用该方法，则业务逻辑（目标方法）则不会执行
            Object result = joinPoint.proceed();

            // 2. 设置响应结果
            // 注意：如果返回结果非常大，建议截断或只记录关键信息，避免数据库字段溢出
            operLog.setResult(JSON.toJSONString(result));

            // 3. 执行时长
            long timeCost = System.currentTimeMillis() - startTime;
            // 可以选择将耗时也存入日志表

            return result;
        } catch (Exception e) {
            // 4. 异常处理
            operLog.setStatus(1);
            operLog.setErrorMsg(e.getMessage());
            throw e;
        } finally {
            // 5. 设置基础信息 (无论成功失败都记录)
            handleLog(joinPoint, operLog, request);

            // 6. 异步入库 (建议使用线程池，避免阻塞主线程)
            new Thread(() -> {
                try {
//                    operLogMapper.insert(operLog);
                    log.info("成功保存到数据库,{}",operLog.toString());
                } catch (Exception ex) {
                    log.error("日志入库失败: {}", ex.getMessage());
                }
            }).start();
        }
    }

    private void handleLog(JoinPoint joinPoint, OperLog operLog, HttpServletRequest request) {
        try {
            // 获得注解
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Log controllerLog = signature.getMethod().getAnnotation(Log.class);
            if (controllerLog == null) {
                return;
            }

            // === 设置请求数据 ===

            // 1. 账号 (这里假设从 Session 或 SecurityContext 获取，需根据你的认证框架修改)
            String account = getAccountFromRequest(request);
            operLog.setAccount(account);

            // 2. IP地址
            String ip = IpUtils.getIpAddr(request);
            operLog.setIp(ip);

            // 3. URL 和 Method
            operLog.setReqUrl(request.getRequestURI());
            operLog.setMethod(request.getMethod());

            // 4. 请求参数
            // 过滤掉 HttpServletRequest 和 HttpServletResponse 对象，防止序列化报错
            Object[] args = joinPoint.getArgs();
            if ("POST".equalsIgnoreCase(request.getMethod()) || "PUT".equalsIgnoreCase(request.getMethod())) {
                String params = argsArrayToString(args);
                operLog.setParams(params);
                log.info("POST和PUT请求，参数：{}", params);
            } else {
                // *** 这里要注意，如果在接口中没有定义参数，此处是不会打印的 ***
                    log.info("GET请求，参数：{}", JSON.toJSONString(args));
                operLog.setParams(JSON.toJSONString(args));
            }


        } catch (Exception exp) {
            log.error("==前置通知异常==", exp);
        }
    }

    // 模拟获取当前登录账号的方法
    private String getAccountFromRequest(HttpServletRequest request) {
        // 实际场景可能是：
        // return StpUtil.getLoginIdAsString(); (Sa-Token)
        // return SecurityContextHolder.getContext().getAuthentication().getName(); (Spring Security)
        // return request.getSession().getAttribute("user");
        return "admin"; // 演示用
    }

    /**
     * 请求参数拼装
     *
     * @param paramsArray
     * @return
     */
    private String argsArrayToString(Object[] paramsArray) {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0) {
            for (int i = 0; i < paramsArray.length; i++) {
                Object obj = paramsArray[i];

                if (obj instanceof ServletRequest) {
                    //过滤 不打印

                } else if (obj instanceof HttpServletResponse) {
                    //过滤 不打印

                } else if (obj instanceof MultipartFile) {
                    //过滤 不打印

                } else if (obj instanceof Model) {
                    //过滤 不打印

                } else if (obj instanceof ModelAndView) {
                    //过滤 不打印

                } else {
                    try {
                        if (obj != null && !"".equals(obj)) {
                            Object jsonObj = JSON.toJSON(obj);
                            params += jsonObj.toString() + ";";
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return params.trim();
    }
}

