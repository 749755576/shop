package com.zixi.shop.dao;

import com.zixi.shop.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 
 * 
 * @author lichanglin
 * @email 749755576@qq.com
 * @date 2020-09-26 23:04:17
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 根据用户Id查询所拥有的权限字符串
     * @param userId
     * @return
     */
    List<String> selectPermsByUserId(Long userId);

    /**
     * 查询树形资源列表
     * @return
     */
    List<SysMenu> selectMenuNormalAll();

    /**
     * 根据用户ID查询所拥有的菜单权限
     * @param userId
     * @return
     */
    List<SysMenu> selectMenusByUserId(Long userId);

    /**
     * 根据角色ID查询所拥有的菜单权限
     * @param roleId
     * @return
     */
    List<SysMenu> selectResourceByRoleId(Long roleId);

    /**
     * 查询所有菜单，包括按钮
     * @return
     */
    List<SysMenu> selectMenuAll();
}
