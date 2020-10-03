package com.zixi.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zixi.shop.common.AppResultData;
import com.zixi.shop.common.PagePar;
import com.zixi.shop.common.TableResultData;
import com.zixi.shop.entity.SysUser;
import com.zixi.shop.entity.vo.AddUserVo;
import com.zixi.shop.entity.vo.UpUserVo;

import java.net.UnknownHostException;
import java.util.Map;

/**
 * 
 *
 * @author lichanglin
 * @email 749755576@qq.com
 * @date 2020-09-26 23:04:17
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 查询用户列表
     * @param pagePar
     * @return
     */
    TableResultData getUserList(PagePar pagePar,String loginName);

    /**
     *删除单个用户
     * @param userId
     * @return
     */
    AppResultData delUserById(Long userId);

    /**
     * 修改用户
     *
     * @param upUserVo
     * @return
     * @throws UnknownHostException
     */
    AppResultData upUserById(UpUserVo upUserVo);

    /**
     * 添加用户
     * @param sysUser
     * @return
     */
    AppResultData addUser(AddUserVo addUserVo);

    /**
     * 修改用户状态
     * @param userId
     * @return
     */
    AppResultData upUserStatusById(Long userId,Integer status);

    /**
     * 批量删除用户
     * @param userId
     * @return
     */
    AppResultData delUserList(String userId);

    /**
     * 批量修改用户状态
     * @param userId
     * @param status
     * @return
     */
    AppResultData upUserStatusList(String userId, Integer status);
}

