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
@TableName("sys_role")
public class SysRole implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 角色ID
	 */
	@TableId(value = "role_id",type = IdType.AUTO)
	private Long roleId;
	/**
	 * 角色名称
	 */
	private String roleName;
	/**
	 * 角色序号
	 */
	private Integer roleSort;
	/**
	 * 权限范围(0:拥有全部权限 1:自定义权限)
	 */
	private Integer dataScope;
	/**
	 * 角色状态（0正常 1停用）
	 */
	private Integer status;
	/**
	 * del_flag
	 */
	private Integer delFlag;
	/**
	 * 创建者
	 */
	private String createBy;
	/**
	 * 创建日期
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 备注
	 */
	private String remark;

}
