package com.zixi.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zixi.shop.common.AppResultData;
import com.zixi.shop.common.PagePar;
import com.zixi.shop.common.TableResultData;
import com.zixi.shop.entity.SysRole;
import com.zixi.shop.entity.vo.AddRoleVo;
import com.zixi.shop.entity.vo.SetRoleVo;
import com.zixi.shop.entity.vo.UpRoleVo;

/**
 * 
 *
 * @author lichanglin
 * @email 749755576@qq.com
 * @date 2020-09-26 23:04:17
 */
public interface SysRoleService extends IService<SysRole> {
    /**
     * 查询角色列表
     * @param pagePar
     * @param roleName
     * @return
     */
    TableResultData getRoleList(PagePar pagePar, String roleName);

    /**
     * 删除单个角色
     * @param roleId
     * @return
     */
    AppResultData delRoleById(Long roleId);

    /**
     * 添加角色
     * @param addRoleVo
     * @return
     */
    AppResultData addRole(AddRoleVo addRoleVo);

    /**
     * 修改角色
     * @param upRoleVo
     * @return
     */
    AppResultData upRoleById(UpRoleVo upRoleVo);

    /**
     * 根据角色ID授权资源
     * @param setRoleVo
     * @return
     */
    AppResultData setRole(SetRoleVo setRoleVo);

    /**
     * 查询角色集合
     * @return
     */
    AppResultData getRole();

    /**
     * 修改角色状态
     * @param roleId
     * @param status
     * @return
     */
    AppResultData upRoleStatusById(Long roleId, Integer status);
}

