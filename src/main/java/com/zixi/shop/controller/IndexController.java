package com.zixi.shop.controller;

import com.zixi.shop.common.AppResultData;
import com.zixi.shop.entity.SysMenu;
import com.zixi.shop.entity.SysUser;
import com.zixi.shop.service.SysMenuService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexController {

    @Autowired
    private SysMenuService sysMenuService;
    @GetMapping("/index")
    @CrossOrigin
    public AppResultData index(){
        //获取用户信息查询该用户的数据权限
        SysUser sysUser=(SysUser)SecurityUtils.getSubject().getPrincipal();
        System.out.println("用户信息是"+sysUser.getLoginName());
        return AppResultData.success("成功",sysMenuService.selectMenusByUser(sysUser));
    }
}
