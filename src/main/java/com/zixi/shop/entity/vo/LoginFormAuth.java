package com.zixi.shop.entity.vo;

import lombok.Data;

/**
 * 登录表单
 */
@Data
public class LoginFormAuth {
    /**
     * 用户名
     */
    private String loginName;

    /**
     * 密码
     */
    private String password;

}
