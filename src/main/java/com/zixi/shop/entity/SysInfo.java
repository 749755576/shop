package com.zixi.shop.entity;

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
@TableName("sys_login_info")
public class SysInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 日志ID
	 */
	@TableId
	private Long infoId;
	/**
	 * 登录账号
	 */
	private String loginName;
	/**
	 * 登录IP地址
	 */
	private String ipaddr;
	/**
	 * 登录状态（0成功 1失败）
	 */
	private String status;
	/**
	 * 访问时间
	 */
	private Date loginTime;

}
