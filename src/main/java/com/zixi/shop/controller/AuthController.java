package com.zixi.shop.controller;

import com.zixi.shop.common.AppResultData;
import com.zixi.shop.config.shiro.ShiroUtils;
import com.zixi.shop.dao.SysUserMapper;
import com.zixi.shop.entity.SysUser;
import com.zixi.shop.entity.vo.LoginFormAuth;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Controller
public class AuthController {

    @Autowired
    private SysUserMapper sysUserMapper;

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "/login";
    }

    /**
     * 登录接口
     * @param loginFormAuth
     * @return
     */
    @PostMapping("/auth")
    @ResponseBody
    @CrossOrigin
    public AppResultData auth(@RequestBody LoginFormAuth loginFormAuth) throws UnknownHostException {
        Subject subject = SecurityUtils.getSubject();
        //封装用户数据
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(loginFormAuth.getLoginName(), ShiroUtils.generatePwdEncrypt(loginFormAuth.getPassword()));
        //RememberMe代表可以直接访问
        usernamePasswordToken.setRememberMe(true);
        //登录成功
        try {
            subject.login(usernamePasswordToken);
            SysUser sysUser = (SysUser)SecurityUtils.getSubject().getPrincipal();
            //获取IP
            String ip = InetAddress.getLocalHost().getHostAddress();
            sysUser.setLoginIp(ip);
            sysUserMapper.updateById(sysUser);
            System.out.println(sysUser.getUserName()+"--------------------------登录成功");
        } catch (IncorrectCredentialsException e) {
            return AppResultData.errorMessage("密码错误");
        } catch (UnknownAccountException e){
            return AppResultData.errorMessage("用户名不存在");
        } catch (DisabledAccountException e){
            return AppResultData.errorMessage("该账户已被停用，请联系管理员");
        } catch (AccountException e){
            return AppResultData.errorMessage("该账户已被永久停封");
        }
        Session session = SecurityUtils.getSubject().getSession();

        return AppResultData.successMessage(session.getId().toString());
    }
}
