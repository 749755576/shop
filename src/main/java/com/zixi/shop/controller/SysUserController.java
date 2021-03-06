package com.zixi.shop.controller;

import com.zixi.shop.common.AppResultData;
import com.zixi.shop.common.PagePar;
import com.zixi.shop.common.TableResultData;
import com.zixi.shop.entity.vo.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.zixi.shop.service.SysUserService;
import javax.validation.Valid;


/**
 * 
 *
 * @author lichanglin
 * @email 749755576@qq.com
 * @date 2020-09-26 23:04:17
 */
@RestController
@RequestMapping("/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 查询用户列表
     * @param pagePar
     * @return
     */
    @GetMapping("/getUserList")
    @RequiresPermissions("system:user:getList")
    public TableResultData getUserList(@Valid PagePar pagePar,String loginName){
        return sysUserService.getUserList(pagePar,loginName);
    }


    /**
     * 删除单个用户
     * @param delUserByIdVo
     * @return
     */
    @PostMapping("/delUserById")
    @RequiresPermissions("system:user:delById")
    public AppResultData delUserById(@RequestBody DelUserByIdVo delUserByIdVo){
        return sysUserService.delUserById(delUserByIdVo);
    }

    /**
     * 修改用户
     * @param upUserVo
     * @return
     */
    @PostMapping("/upUserById")
    @RequiresPermissions("system:user:upById")
    public AppResultData upUserById(@RequestBody UpUserVo upUserVo){
        return sysUserService.upUserById(upUserVo);
    }

    /**
     * 添加用户
     * @param addUserVo
     * @return
     */
    @PostMapping("/addUser")
    @RequiresPermissions("system:user:add")
    public AppResultData addUser(@RequestBody AddUserVo addUserVo) {
        return sysUserService.addUser(addUserVo);
    }

    /**
     * 修改单个用户状态
     * @param upUserStatusByIdVo
     * @return
     */
    @PostMapping("/upUserStatusById")
    @RequiresPermissions("system:user:upUserStatusById")
    public AppResultData upUserStatusById(@RequestBody UpUserStatusByIdVo upUserStatusByIdVo) {
        return sysUserService.upUserStatusById(upUserStatusByIdVo);
    }

    /**
     * 批量删除用户
     * @param userId
     * @return
     */
    @PostMapping("/delUserList")
    @RequiresPermissions("system:user:delUserList")
    public AppResultData delUserList(String userId) {
        return sysUserService.delUserList(userId);
    }

    /**
     * 批量修改用户状态
     * @param userId
     * @return
     */
    @PostMapping("/upUserStatusList")
    @RequiresPermissions("system:user:upUserStatusList")
    public AppResultData upUserStatusList(String userId,Integer status) {
        return sysUserService.upUserStatusList(userId,status);
    }

    /**
     * 重置用户密码
     * @param upUserPasswordByIdVo
     * @return
     */
    @PostMapping("/upUserPasswordById")
    @RequiresPermissions("system:user:upUserPasswordById")
    public AppResultData upUserPasswordById(@RequestBody UpUserPasswordByIdVo upUserPasswordByIdVo) {
        return sysUserService.upUserPasswordById(upUserPasswordByIdVo);
    }

}
