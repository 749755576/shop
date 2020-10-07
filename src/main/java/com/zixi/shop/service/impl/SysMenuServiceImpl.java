package com.zixi.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zixi.shop.common.AppResultData;
import com.zixi.shop.dao.SysRoleMenuMapper;
import com.zixi.shop.entity.SysRoleMenu;
import com.zixi.shop.entity.SysUser;
import com.zixi.shop.entity.vo.AddMenuVo;
import com.zixi.shop.entity.vo.UpMenuVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zixi.shop.dao.SysMenuMapper;
import com.zixi.shop.entity.SysMenu;
import com.zixi.shop.service.SysMenuService;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service("menuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public AppResultData selectMenusByUser(SysUser sysUser) {
        List<SysMenu> menus = new LinkedList<SysMenu>();
        if (sysUser==null){
            return AppResultData.errorMessage("请您先登录");
        }
        if (sysUser.getUserType()==0 && sysUser!=null){
            menus=sysMenuMapper.selectMenuNormalAll();
        }else {
            menus = sysMenuMapper.selectMenusByUserId(sysUser.getUserId());
        }
        List<SysMenu> childPerms = getChildPerms(menus, 0);
        return AppResultData.success("成功",childPerms);
    }

    @Override
    public AppResultData selectMenuList() {
        List<SysMenu> sysMenus = sysMenuMapper.selectMenuAll();
        List<SysMenu> childPerms = getChildPerms(sysMenus, 0);
        return AppResultData.success("成功",childPerms);
    }

    @Override
    @Transactional
    public AppResultData delMenuById(Long menuId) {
        if (menuId==null){
            return AppResultData.errorMessage("资源ID为空");
        }
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("parent_id", menuId);
        List<SysMenu> sele = sysMenuMapper.selectByMap(columnMap);
        if (sele.size()>0){
            return AppResultData.errorMessage("该资源存在子资源，请先删除子资源");
        }
        QueryWrapper<SysRoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("menu_id", menuId);
        sysRoleMenuMapper.delete(queryWrapper);
        SysMenu sysMenu=new SysMenu();
        sysMenu.setMenuId(menuId);
        sysMenuMapper.deleteById(sysMenu);
        return AppResultData.successMessage("删除成功");
    }

    @Override
    @Transactional
    public AppResultData upMenuById(UpMenuVo upMenuVo) {
        if (upMenuVo.getMenuId()==null){
            return AppResultData.errorMessage("资源ID为空");
        }
        if (upMenuVo.getMenuId()==upMenuVo.getParentId()){
            return AppResultData.errorMessage("不可以将自身作为自己的父类");
        }
        Map<String, Object> columnMap = new HashMap<>();
        Map<String, Object> columnMap1 = new HashMap<>();
        columnMap.put("menu_name", upMenuVo.getMenuName());
        columnMap1.put("perms", upMenuVo.getPerms());
        List<SysMenu> sele = sysMenuMapper.selectByMap(columnMap);
        SysMenu sysMenu=new SysMenu();
        if (sele.size() != 0) {
            String name=null;
            String code=null;
            for(SysMenu sm: sele){
                name=sm.getMenuName();
                code=sm.getPerms();
            }
            if (!name.equals(upMenuVo.getMenuName())|| !code.equals(upMenuVo.getPerms())){
                return AppResultData.errorMessage("资源名称或者资源权限字符串已存在，请输入新的信息");
            }else {
                BeanUtils.copyProperties(upMenuVo,sysMenu);
                sysMenu.setUpdateTime(new Date());
                sysMenuMapper.updateById(sysMenu);
            }
        }else {
            sysMenuMapper.updateById(sysMenu);
        }

        return AppResultData.successMessage("修改成功");
    }

    @Override
    public AppResultData addMenu(AddMenuVo addMenuVo) {
        Map<String, Object> columnMap = new HashMap<>();
        Map<String, Object> columnMap1 = new HashMap<>();
        columnMap.put("menu_name", addMenuVo.getMenuName());
        columnMap1.put("perms", addMenuVo.getPerms());
        List<SysMenu> sele = sysMenuMapper.selectByMap(columnMap);
        List<SysMenu> sele1 = sysMenuMapper.selectByMap(columnMap1);
        if (sele.size() != 0) {
            return AppResultData.errorMessage("资源名称已存在");
        }
        if(sele1.size()!=0){
            return AppResultData.errorMessage("权限字符串已存在");
        }else {
            SysMenu sysMenu=new SysMenu();
            BeanUtils.copyProperties(addMenuVo,sysMenu);
            SysUser sysUser =(SysUser) SecurityUtils.getSubject().getPrincipal();
            sysMenu.setCreateBy(sysUser.getLoginName());
            sysMenu.setCreateTime(new Date());
            sysMenuMapper.insert(sysMenu);
        }
        return AppResultData.successMessage("添加成功");
    }

    @Override
    public AppResultData getMenuListByRoleId(Long roleId) {
        List<SysMenu> menus = new LinkedList<SysMenu>();
        menus = sysMenuMapper.selectMenuAll();
        List<SysMenu> menuRole = new LinkedList<SysMenu>();
        if (roleId == null){
            return AppResultData.success("成功",menus);
        }else{
            menuRole=sysMenuMapper.selectResourceByRoleId(roleId);
            for (SysMenu rol:menuRole) {
                for (SysMenu men:menus) {
                    if(rol.getMenuId() == men.getMenuId()){
                        men.setVisible(1);
                    }
                }
            }
        }

        List<SysMenu> roleMenuByChecked = getChildPerms(menus, 0);
        return AppResultData.success("成功",roleMenuByChecked);
    }


    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list 分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<SysMenu> getChildPerms(List<SysMenu> list, int parentId)
    {
        List<SysMenu> returnList = new ArrayList<SysMenu>();
        for (Iterator<SysMenu> iterator = list.iterator(); iterator.hasNext();)
        {
            SysMenu t = (SysMenu) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId)
            {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<SysMenu> list, SysMenu t)
    {
        // 得到子节点列表
        List<SysMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenu tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                // 判断是否有子节点
                Iterator<SysMenu> it = childList.iterator();
                while (it.hasNext())
                {
                    SysMenu n = (SysMenu) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t)
    {
        List<SysMenu> tlist = new ArrayList<SysMenu>();
        Iterator<SysMenu> it = list.iterator();
        while (it.hasNext())
        {
            SysMenu n = (SysMenu) it.next();
            if (n.getParentId().longValue() == t.getMenuId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }
}