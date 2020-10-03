package com.zixi.shop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author lichanglin
 * @email 749755576@qq.com
 * @date 2020-09-26 23:04:17
 */
@Data
@TableName("sys_user")
public class SysUser implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户Id
	 */
	@TableId(value = "user_id",type = IdType.AUTO)
	private Long userId;
	/**
	 * 登录账号
	 */
	private String loginName;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 密码
	 */
	private String password;
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
	 * 用户状态(0:可用 1:禁用)
	 */
	private Integer status;
	/**
	 * 删除标识符(0:存在 1:删除)
	 */
	private Integer delFlag;
	/**
	 * 最后登录IP
	 */
	private String loginIp;
	/**
	 * 创建者
	 */
	private String createBy;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 备注
	 */
	private String remark;

}
