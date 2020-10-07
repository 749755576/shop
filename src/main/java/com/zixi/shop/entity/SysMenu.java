package com.zixi.shop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 
 * 
 * @author lichanglin
 * @email 749755576@qq.com
 * @date 2020-09-26 23:04:17
 */
@Data
@TableName("sys_menu")
public class SysMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 菜单ID
	 */
	@TableId(value = "menu_id",type = IdType.AUTO)
	private Long menuId;
	/**
	 * 菜单名称
	 */
	private String menuName;
	/**
	 * 父菜单ID
	 */
	private Long parentId;
	/**
	 * 显示顺序
	 */
	private Integer orderNum;
	/**
	 * 请求地址
	 */
	private String url;
	/**
	 * 菜单类型（M目录 C菜单 F按钮）
	 */
	private String menuType;
	/**
	 * 菜单状态（0显示 1隐藏）
	 */
	private Integer visible;
	/**
	 * 权限标识
	 */
	private String perms;
	/**
	 * 菜单图标
	 */
	private String icon;
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

	/** 子菜单 */
	@TableField(exist = false)
	private List<SysMenu> children = new ArrayList<SysMenu>();

}
