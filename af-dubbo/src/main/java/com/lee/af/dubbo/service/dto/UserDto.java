package com.lee.af.dubbo.service.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 必须实现Serializable接口，并显式声明serialVersionUID（避免序列化版本冲突）：
 */
@Data
@Schema
public class UserDto implements Serializable {
    // 显式声明序列化版本号（建议手动指定，避免自动生成导致不一致）
    /**
     * 序列化过程中会将对象的状态（包括类名和serialVersionUID）写入字节流，反序列化时，JVM会加载当前的类，并将类中声明的serialVersionUID
     * 与字节流中存储的版本号进行比较。如果两者完全匹配，反序列化才能成功进行。
     * serialVersionUID 本质上是序列化类的“版本号”，它通过显式控制版本标识，确保了在类定义发生变化时，反序列化操作仍能安全、可靠地进行
     */
    @Serial
    private static final long serialVersionUID = 1L;
    @Schema(title="id")
    private Integer id;

    @Schema(title="用户名")
    private String username;

    public UserDto() {
    }

    public UserDto(Integer id, String username) {
        this.id = id;
        this.username = username;
    }
}
