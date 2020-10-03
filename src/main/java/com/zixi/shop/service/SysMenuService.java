package com.zixi.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zixi.shop.common.AppResultData;
import com.zixi.shop.entity.SysMenu;
import com.zixi.shop.entity.SysUser;

import java.util.List;

/**
 * 
 *
 * @author lichanglin
 * @email 749755576@qq.com
 * @date 2020-09-26 23:04:17
 */
public interface SysMenuService extends IService<SysMenu> {
    /**
     * 根据用户ID查询该用户的菜单权限
     * @param sysUser
     * @return
     */
    AppResultData selectMenusByUser(SysUser sysUser);
}

