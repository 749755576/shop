package com.zixi.shop.dao;

import com.zixi.shop.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author lichanglin
 * @email 749755576@qq.com
 * @date 2020-09-26 23:04:17
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 查询用户信息
     * @param loginName
     * @return
     */
    SysUser findByLoginName(String loginName);

}
