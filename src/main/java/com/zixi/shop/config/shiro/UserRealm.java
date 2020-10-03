package com.zixi.shop.config.shiro;

import com.zixi.shop.common.AppResultData;
import com.zixi.shop.dao.SysMenuMapper;
import com.zixi.shop.dao.SysUserMapper;
import com.zixi.shop.dao.SysUserRoleMapper;
import com.zixi.shop.entity.SysUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 自定义Realm
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;
    /**
     * 执行授权逻辑
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        SysUser user = (SysUser) principalCollection.getPrimaryPrincipal();
        //查询用户
        Map<String, Object> map = new HashMap<>();
        map.put("login_name", user.getLoginName());
        SysUser sysUser = sysUserMapper.selectByMap(map).get(0);
        //查询该用户所拥有的权限（标识符）
        List<String> pre = sysMenuMapper.selectPermsByUserId(sysUser.getUserId());

        Set<String> preList = new HashSet<>();
        for (String s : pre) {
            preList.add(s);
        }
        authorizationInfo.addStringPermissions(preList);
        return authorizationInfo;
    }

    /**
     * 执行认证逻辑
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        /**
         * 将UsernamePasswordToken传过来的账号密码进入认证逻辑
         */
        UsernamePasswordToken token=(UsernamePasswordToken)authenticationToken;
        /**
         * 判断用户名是否存在
         */
        Map<String, Object> map = new HashMap<>();
        map.put("login_name", token.getUsername());
        if(sysUserMapper.selectByMap(map).size()==0){
            return null;
        }
        SysUser sysUser = sysUserMapper.selectByMap(map).get(0);
        String loginName = sysUser.getLoginName();
        if(sysUser.getStatus() == 1){
            throw new DisabledAccountException("该用户已被停用，请联系管理员");
        }
        if(sysUser.getDelFlag() == 1){
            throw new AccountException("该用户已经被永久封停");
        }




        return new SimpleAuthenticationInfo(sysUser,sysUser.getPassword(),this.getClass().getName());
    }
}
