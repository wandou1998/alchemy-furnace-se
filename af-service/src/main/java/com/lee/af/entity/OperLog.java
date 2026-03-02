package com.lee.af.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
/* @TableName("sys_oper_log") */
public class OperLog {
/*    @TableId(type = IdType.AUTO) */
    private Long id;

    private String account; // 账号
    private String ip;      // IP地址
    private String reqUrl;     // 接口地址
    private String method;  // 请求方式
    private String params;  // 参数
    private String result;  // 响应结果
    private Integer status; // 状态
    private String errorMsg;// 错误信息
    private LocalDateTime operTime; // 操作时间
}
