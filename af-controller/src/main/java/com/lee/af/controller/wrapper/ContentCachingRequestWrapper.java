package com.lee.af.controller.wrapper;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * ContentCachingRequestWrapper 是一个 HttpServletRequest 的包装类，
 * 用于缓存请求体的内容，使得请求体可以被多次读取。
 */
public class ContentCachingRequestWrapper  extends HttpServletRequestWrapper {
    // 缓存请求内容的字节数组
    private final byte[] body;

    /**
     * 构造函数，初始化请求包装器并缓存请求体
     * @param request 原始的 HttpServletRequest 对象
     * @throws IOException 如果读取请求体时发生I/O错误
     */
    public ContentCachingRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        // 读取请求体并缓存
        body = readRequestBody(request).getBytes(StandardCharsets.UTF_8);
    }


    /**
     * 重写getInputStream方法，用于获取Servlet输入流
     * @return ServletInputStream 返回Servlet输入流
     * @throws IOException 如果发生I/O错误则抛出此异常
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 使用字节数组创建输入流，body是预先存储的请求体数据
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
        // 返回一个匿名的ServletInputStream实现
        return new ServletInputStream() {
            /**
             * 检查输入流是否已读取完毕
             * @return 如果没有更多数据可读则返回true，否则返回false
             */
            @Override
            public boolean isFinished() {
                return byteArrayInputStream.available() == 0;
            }

            /**
             * 检查输入流是否准备好读取
             * @return 总是返回true，表示数据总是可读的
             */
            @Override
            public boolean isReady() {
                return true;
            }

            /**
             * 设置读取监听器（在此实现中为空）
             * @param readListener 读取监听器（在此实现中未使用）
             */
            @Override
            public void setReadListener(ReadListener readListener) {}

            /**
             * 从输入流中读取一个字节的数据
             * @return 读取到的字节，如果到达流末尾则返回-1
             * @throws IOException 如果发生I/O错误则抛出此异常
             */
            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream(), StandardCharsets.UTF_8));
    }

    // 辅助方法：读取原始请求体
    private String readRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    // 获取缓存的请求体
    public String getBody() {
        return new String(body, StandardCharsets.UTF_8);
    }
}
