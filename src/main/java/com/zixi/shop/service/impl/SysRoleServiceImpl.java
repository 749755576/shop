package com.zixi.shop.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zixi.shop.common.AppResultData;
import com.zixi.shop.common.PagePar;
import com.zixi.shop.common.TableResultData;
import com.zixi.shop.dao.SysRoleMenuMapper;
import com.zixi.shop.entity.SysRoleMenu;
import com.zixi.shop.entity.SysUser;
import com.zixi.shop.entity.vo.AddRoleVo;
import com.zixi.shop.entity.vo.SetRoleVo;
import com.zixi.shop.entity.vo.UpRoleVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zixi.shop.dao.SysRoleMapper;
import com.zixi.shop.entity.SysRole;
import com.zixi.shop.service.SysRoleService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("roleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;


    @Override
    public TableResultData getRoleList(PagePar pagePar, String roleName) {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        if (roleName==null||roleName.equals("")){
            //查询未删除的角色
            queryWrapper.eq("del_flag",0);
        }else {
            //查询未删除的角色
            queryWrapper
                    .eq("del_flag",0)
                    .like("login_name",roleName);
        }
        IPage<SysRole> sele = sysRoleMapper.selectPage(new Page<SysRole>(pagePar.getPage(), pagePar.getPageSize()), queryWrapper);
        List<SysRole> seleL = sele.getRecords();
        return TableResultData.success(sele.getTotal(),seleL);
    }

    @Override
    @Transactional
    public AppResultData delRoleById(Long roleId) {
        if (roleId==null){
            return AppResultData.errorMessage("角色Id为空");
        }
        SysRole sysRole=new SysRole();
        sysRole.setDelFlag(1);
        sysRoleMapper.updateById(sysRole);
        return AppResultData.successMessage("删除成功");
    }

    @Override
    @Transactional
    public AppResultData addRole(AddRoleVo addRoleVo) {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("role_name", addRoleVo.getRoleName());
        List<SysRole> sele = baseMapper.selectByMap(columnMap);
        if (sele.size()!=0){
            return AppResultData.errorMessage("角色名称已存在，请重新输入");
        }
        SysRole sysRole=new SysRole();
        BeanUtil.copyProperties(addRoleVo,sysRole);
        SysUser admin =(SysUser) SecurityUtils.getSubject().getPrincipal();
        sysRole.setCreateBy(admin.getLoginName());
        sysRole.setCreateTime(new Date());
        sysRoleMapper.insert(sysRole);
        int comma = addRoleVo.getId().indexOf(",");
        setRoleById(comma,addRoleVo.getId(),sysRole.getRoleId());
        return AppResultData.successMessage("添加成功");
    }

    @Override
    @Transactional
    public AppResultData upRoleById(UpRoleVo upRoleVo) {
        if (upRoleVo.getRoleId()==null){
            return AppResultData.errorMessage("角色Id为空");
        }
        SysRole sysRole=new SysRole();
        BeanUtil.copyProperties(upRoleVo,sysRole);
        sysRole.setRoleId(upRoleVo.getRoleId());
        sysRole.setUpdateTime(new Date());
        sysRoleMapper.updateById(sysRole);
        QueryWrapper<SysRoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", sysRole.getRoleId());
        sysRoleMenuMapper.delete(queryWrapper);
        int comma = upRoleVo.getId().indexOf(",");
        setRoleById(comma,upRoleVo.getId(),sysRole.getRoleId());
        return AppResultData.successMessage("成功");
    }

    @Override
    @Transactional
    public AppResultData setRole(SetRoleVo setRoleVo) {
        if (setRoleVo.getRoleId()==null){
            return AppResultData.errorMessage("角色ID为空");
        }
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("role_id", setRoleVo.getRoleId());
        sysRoleMapper.deleteByMap(columnMap);
        int comma = setRoleVo.getId().indexOf(",");
        setRoleById(comma,setRoleVo.getId(),setRoleVo.getRoleId());
        return AppResultData.successMessage("授权成功");
    }

    @Override
    public AppResultData getRole() {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("del_flag",0)
                .eq("status",0);
        List<SysRole> sysRoles = sysRoleMapper.selectList(queryWrapper);
        return AppResultData.success("成功",sysRoles);
    }

    @Override
    @Transactional
    public AppResultData upRoleStatusById(Long roleId, Integer status) {
        if (roleId==null){
            return AppResultData.errorMessage("角色Id为空");
        }
        SysRole sysRole=new SysRole();
        sysRole.setRoleId(roleId);
        if (status==0){
            //为防止进入else（失败）,所以不可以在上面直接赋值更新时间
            sysRole.setUpdateTime(new Date());
            sysRole.setStatus(1);
        }else if (status==1){
            sysRole.setUpdateTime(new Date());
            sysRole.setStatus(0);
        }else {
            return AppResultData.errorMessage("修改角色状态失败");
        }
        sysRoleMapper.updateById(sysRole);
        return AppResultData.successMessage("修改角色状态成功");
    }

    /**
     * 添加角色，修改角色，角色授权,抽出出来的授权资源的公共代码
     * @param comma
     * @param menuId
     * @param roleId
     */
    private void setRoleById(int comma,String menuId,Long roleId){
        if (comma==-1){
            if (menuId!=null||!menuId.equals("")){
                SysRoleMenu sysRoleMenu=new SysRoleMenu();
                sysRoleMenu.setRoleId(roleId);
                Long id = Long.valueOf(menuId);
                sysRoleMenu.setId(id);
                sysRoleMenuMapper.insert(sysRoleMenu);
            }

        }else {
            String[] s = menuId.split(",");
            for (String string : s) {
                Long id = Long.valueOf(string);
                SysRoleMenu sysRoleMenu=new SysRoleMenu();
                sysRoleMenu.setRoleId(roleId);
                sysRoleMenu.setId(id);
                sysRoleMenuMapper.insert(sysRoleMenu);
            }
        }
    }

}