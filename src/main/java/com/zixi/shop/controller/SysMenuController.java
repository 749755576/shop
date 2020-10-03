package com.zixi.shop.controller;


import com.zixi.shop.common.AppResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    @GetMapping("/test")
    public void test(){
        System.out.println("好好好");
    }

}
