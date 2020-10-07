package com.zixi.shop.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zixi.shop.common.AppResultData;
import com.zixi.shop.common.PagePar;
import com.zixi.shop.common.TableResultData;
import com.zixi.shop.config.shiro.ShiroUtils;
import com.zixi.shop.dao.SysUserRoleMapper;
import com.zixi.shop.entity.SysUserRole;
import com.zixi.shop.entity.vo.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zixi.shop.dao.SysUserMapper;
import com.zixi.shop.entity.SysUser;
import com.zixi.shop.service.SysUserService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("userService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;


    @Override
    public TableResultData getUserList(PagePar pagePar,String loginName) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        if (loginName==null||loginName.equals("")){
            //查询未删除的用户，忽略查询password
            queryWrapper
                    .eq("del_flag",0)
                    .select(SysUser.class, info -> !info.getColumn().equals("password"));
        }else {
            //查询未删除的用户，忽略查询password
            queryWrapper
                    .eq("del_flag",0)
                    .select(SysUser.class, info -> !info.getColumn().equals("password"))
                    .like("login_name",loginName);
        }
        IPage<SysUser> sele = sysUserMapper.selectPage(new Page<SysUser>(pagePar.getPage(), pagePar.getPageSize()), queryWrapper);
        List<SysUser> seleL = sele.getRecords();
        return TableResultData.success(seleL.size(),seleL);
    }

    @Override
    @Transactional
    public AppResultData delUserById(DelUserByIdVo delUserByIdVo) {
        if (delUserByIdVo.getUserId()==null){
            return AppResultData.errorMessage("用户Id为空");
        }
            SysUser sysUser=new SysUser();
            sysUser.setUserId(delUserByIdVo.getUserId());
            sysUser.setDelFlag(1);
            sysUserMapper.updateById(sysUser);
        return AppResultData.successMessage("删除成功");
    }

    @Override
    @Transactional
    public AppResultData upUserById(UpUserVo upUserVo){
        if (upUserVo.getUserId()==null){
            return AppResultData.errorMessage("用户Id为空");
        }
        SysUser sysUser=new SysUser();
        BeanUtil.copyProperties(upUserVo,sysUser);
        sysUser.setUpdateTime(new Date());
        sysUserMapper.updateById(sysUser);
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("user_id", upUserVo.getUserId());
        sysUserRoleMapper.deleteByMap(columnMap);
        SysUserRole sysUserRole=new SysUserRole();
        sysUserRole.setRoleId(upUserVo.getRoleId());
        sysUserRole.setUserId(upUserVo.getUserId());
        sysUserRoleMapper.insert(sysUserRole);
        return AppResultData.successMessage("修改成功");
    }

    @Override
    @Transactional
    public AppResultData addUser(AddUserVo addUserVo) {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("login_name", addUserVo.getLoginName());
        List<SysUser> sele = baseMapper.selectByMap(columnMap);
        if (sele.size()!=0){
            return AppResultData.errorMessage("用户名已存在，请重新输入");
        }
        SysUser sysUser=new SysUser();
        BeanUtil.copyProperties(addUserVo,sysUser);
        sysUser.setPassword(ShiroUtils.generatePwdEncrypt("123456"));
        sysUser.setCreateTime(new Date());
        SysUser admin =(SysUser) SecurityUtils.getSubject().getPrincipal();
        sysUser.setCreateBy(admin.getLoginName());
        sysUserMapper.insert(sysUser);
        SysUserRole sysUserRole=new SysUserRole();
        sysUserRole.setUserId(sysUser.getUserId());
        sysUserRole.setRoleId(addUserVo.getRoleId());
        sysUserRoleMapper.insert(sysUserRole);
        return AppResultData.successMessage("添加成功");
    }

    @Override
    @Transactional
    public AppResultData upUserStatusById(UpUserStatusByIdVo upUserStatusByIdVo) {
        if (upUserStatusByIdVo.getUserId()==null){
            return AppResultData.errorMessage("用户Id为空");
        }
        SysUser sysUser=new SysUser();
        sysUser.setUserId(upUserStatusByIdVo.getUserId());
        if (upUserStatusByIdVo.getStatus()==0){
            //为防止进入else（失败）,所以不可以在上面直接赋值更新时间
            sysUser.setUpdateTime(new Date());
            sysUser.setStatus(0);
        }else if (upUserStatusByIdVo.getStatus()==1){
            sysUser.setUpdateTime(new Date());
            sysUser.setStatus(1);
        }else {
            return AppResultData.errorMessage("修改用户状态失败");
        }
        sysUserMapper.updateById(sysUser);
        return AppResultData.successMessage("修改用户状态成功");
    }

    @Override
    @Transactional
    public AppResultData delUserList(String userId) {
        if (userId==null||userId.equals("")){
            return AppResultData.errorMessage("用户Id为空");
        }
        int comma = userId.indexOf(",");
        //等于-1说明只选中了一个用户,所以不用循环
        if (comma==-1){
            SysUser sysUser=new SysUser();
            Long id = Long.valueOf(userId);
            sysUser.setUserId(id);
            sysUser.setDelFlag(1);
            sysUserMapper.updateById(sysUser);
        }else {
            String[] split = userId.split(",");
            for (int i=0;i<split.length;i++){
                Long id = Long.valueOf(split[i]);
                SysUser sysUser=new SysUser();
                sysUser.setUserId(id);
                sysUser.setDelFlag(1);
                sysUserMapper.updateById(sysUser);
            }
        }
        return AppResultData.successMessage("删除成功");
    }

    @Override
    @Transactional
    public AppResultData upUserStatusList(String userId, Integer status) {
        if (userId==null||userId.equals("")){
            return AppResultData.errorMessage("用户Id为空");
        }
        if (status==null){
            return AppResultData.errorMessage("状态参数为空");
        }
        int comma = userId.indexOf(",");
        //等于-1说明只选中了一个用户,所以不用循环
        if (comma==-1){
            SysUser sysUser=new SysUser();
            Long id = Long.valueOf(userId);
            sysUser.setUserId(id);
            sysUser.setStatus(status);
            sysUserMapper.updateById(sysUser);
        }else {
            String[] split = userId.split(",");
            for (int i=0;i<split.length;i++){
                Long id = Long.valueOf(split[i]);
                SysUser sysUser=new SysUser();
                sysUser.setUserId(id);
                sysUser.setStatus(status);
                sysUserMapper.updateById(sysUser);
            }
        }
        return AppResultData.successMessage("修改用户状态成功");
    }

    @Override
    public AppResultData upUserPasswordById(UpUserPasswordByIdVo upUserPasswordByIdVo) {
        if (upUserPasswordByIdVo.getUserId()==null){
            return AppResultData.errorMessage("用户Id为空");
        }
        SysUser sysUser=new SysUser();
        sysUser.setUserId(upUserPasswordByIdVo.getUserId());
        sysUser.setPassword(ShiroUtils.generatePwdEncrypt(upUserPasswordByIdVo.getPassword()));
        sysUserMapper.updateById(sysUser);
        return AppResultData.successMessage("重置密码成功");
    }
}