package com.zixi.shop.controller;


import com.zixi.shop.common.AppResultData;
import com.zixi.shop.entity.vo.AddMenuVo;
import com.zixi.shop.entity.vo.UpMenuVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zixi.shop.service.SysMenuService;


/**
 * 
 *
 * @author lichanglin
 * @email 749755576@qq.com
 * @date 2020-09-26 23:04:17
 */
@RestController
@RequestMapping("/menu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 查询资源列表
     * @return
     */
    @GetMapping("/getMenuList")
    @RequiresPermissions("system:menu:getList")
    public AppResultData getMenuList(){
        return sysMenuService.selectMenuList();
    }

    /**
     * 根据角色查询资源列表
     * @return
     */
    @GetMapping("/getMenuListByRoleId")
    @RequiresPermissions("system:menu:getListByRoleId")
    public AppResultData getMenuListByRoleId(Long roleId){
        return sysMenuService.getMenuListByRoleId(roleId);
    }

    /**
     * 删除资源
     * @param menuId
     * @return
     */
    @PostMapping("/delMenuById")
    @RequiresPermissions("system:menu:delById")
    public AppResultData delMenuById(Long menuId){
        return sysMenuService.delMenuById(menuId);
    }

    /**
     * 修改资源
     * @param upMenuVo
     * @return
     */
    @PostMapping("/upMenuById")
    @RequiresPermissions("system:menu:upById")
    public AppResultData upMenuById(UpMenuVo upMenuVo){
        return sysMenuService.upMenuById(upMenuVo);
    }

    /**
     * 添加资源
     * @param addMenuVo
     * @return
     */
    @PostMapping("/addMenu")
    @RequiresPermissions("system:menu:add")
    public AppResultData addMenu(AddMenuVo addMenuVo) {
        return sysMenuService.addMenu(addMenuVo);
    }

}
