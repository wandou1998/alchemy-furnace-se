package com.lee.af.dubbo.service.dto;


import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 必须实现Serializable接口，并显式声明serialVersionUID（避免序列化版本冲突）：
 */
@Data
public class UserDto implements Serializable {
    // 显式声明序列化版本号（建议手动指定，避免自动生成导致不一致）
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String username;

    public UserDto() {
    }

    public UserDto(Integer id, String username) {
        this.id = id;
        this.username = username;
    }
}
