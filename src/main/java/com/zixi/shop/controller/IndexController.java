package com.zixi.shop.controller;

import com.zixi.shop.common.AppResultData;
import com.zixi.shop.entity.SysUser;
import com.zixi.shop.service.SysMenuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class IndexController {

    @Autowired
    private SysMenuService sysMenuService;
    @GetMapping("/index")
    @CrossOrigin
    @RequiresAuthentication
    public AppResultData index(HttpServletRequest request){
        System.out.println("request uri:\t"+request.getRequestURI());
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies){
            System.out.println("request uri:\t"+cookie.getName()+":"+cookie.getValue());
        }
        //获取用户信息查询该用户的数据权限
        SysUser sysUser=(SysUser)SecurityUtils.getSubject().getPrincipal();
        System.out.println("用户信息是"+sysUser.getLoginName());
        return AppResultData.success("成功",sysMenuService.selectMenusByUser(sysUser));
    }
}
