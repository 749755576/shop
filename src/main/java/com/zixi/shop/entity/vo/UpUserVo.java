package com.zixi.shop.entity.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UpUserVo {
    private Long userId;
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
     * 更新时间
     */
    private Date updateTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 角色Id
     */
    private Long roleId;
}
