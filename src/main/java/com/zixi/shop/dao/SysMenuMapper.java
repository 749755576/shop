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

    List<SysMenu> selectMenuNormalAll();

    List<SysMenu> selectMenusByUserId(Long userId);
}
