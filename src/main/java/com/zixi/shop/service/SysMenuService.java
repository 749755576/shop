package com.zixi.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zixi.shop.common.AppResultData;
import com.zixi.shop.entity.SysMenu;
import com.zixi.shop.entity.SysUser;
import com.zixi.shop.entity.vo.AddMenuVo;
import com.zixi.shop.entity.vo.UpMenuVo;

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

    /**
     * 查询树形资源列表
     * @return
     */
    AppResultData selectMenuList();

    /**
     * 删除资源
     * @param menuId
     * @return
     */
    AppResultData delMenuById(Long menuId);

    /**
     * 修改资源
     * @param upMenuVo
     * @return
     */
    AppResultData upMenuById(UpMenuVo upMenuVo);

    /**
     * 添加资源
     * @param addMenuVo
     * @return
     */
    AppResultData addMenu(AddMenuVo addMenuVo);

    /**
     * 根据角色ID查询资源
     * @param roleId
     * @return
     */
    AppResultData getMenuListByRoleId(Long roleId);

    /**
     * 查询普通资源列表
     * @return
     */
    AppResultData selectList();
}

