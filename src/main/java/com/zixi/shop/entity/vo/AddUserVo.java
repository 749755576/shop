package com.zixi.shop.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class AddUserVo {
    /**
     * 登录账号
     */
    private String loginName;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户类型(0:系统管理员 1:普通用户 3:供货商 )
     */
    private Integer userType;
    /**
     * 性别(0:男 1:女 2:未知)
     */
    private Integer sex;
    /**
     * 头像地址
     */
    private String avatar;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 角色ID
     */
    private Long roleId;
}
