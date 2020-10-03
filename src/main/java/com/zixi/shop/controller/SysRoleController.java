package com.zixi.shop.controller;



import com.zixi.shop.common.AppResultData;
import com.zixi.shop.common.PagePar;
import com.zixi.shop.common.TableResultData;
import com.zixi.shop.entity.vo.AddRoleVo;
import com.zixi.shop.entity.vo.SetRoleVo;
import com.zixi.shop.entity.vo.UpRoleVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zixi.shop.service.SysRoleService;

import javax.validation.Valid;


/**
 * 
 *
 * @author lichanglin
 * @email 749755576@qq.com
 * @date 2020-09-26 23:04:17
 */
@RestController
@RequestMapping("/role")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 查询角色列表
     * @param pagePar
     * @return
     */
    @GetMapping("/getRoleList")
    @RequiresPermissions("system:role:getList")
    public TableResultData getRoleList(@Valid PagePar pagePar, String roleName){
        return sysRoleService.getRoleList(pagePar,roleName);
    }

    /**
     * 查询角色集合
     * @return
     */
    @GetMapping("/getRole")
    @RequiresPermissions("system:role:list")
    public AppResultData getRole(){
        return sysRoleService.getRole();
    }

    /**
     * 删除单个角色
     * @param roleId
     * @return
     */
    @PostMapping("/delRoleById")
    @RequiresPermissions("system:role:delById")
    public AppResultData delRoleById(Long roleId){
        return sysRoleService.delRoleById(roleId);
    }

    /**
     * 添加角色
     * @param addRoleVo
     * @return
     */
    @PostMapping("/addRole")
    @RequiresPermissions("system:role:addRole")
    public AppResultData addRole(AddRoleVo addRoleVo){
        return sysRoleService.addRole(addRoleVo);
    }

    /**
     * 根据角色ID授权资源
     * @param setRoleVo
     * @return
     */
    @PostMapping("/setRole")
    @RequiresPermissions("system:role:setRole")
    public AppResultData setRole(SetRoleVo setRoleVo){
        return sysRoleService.setRole(setRoleVo);
    }

    /**
     * 修改角色
     * @param upRoleVo
     * @return
     */
    @PostMapping("/upRoleById")
    @RequiresPermissions("system:role:upById")
    public AppResultData upRoleById(UpRoleVo upRoleVo){
        return sysRoleService.upRoleById(upRoleVo);
    }


}
