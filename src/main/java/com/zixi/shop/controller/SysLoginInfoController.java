package com.zixi.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zixi.shop.service.SysLoginInfoService;



/**
 * 
 *
 * @author lichanglin
 * @email 749755576@qq.com
 * @date 2020-09-26 23:04:17
 */
@RestController
@RequestMapping("shop/logininfo")
public class SysLoginInfoController {
    @Autowired
    private SysLoginInfoService sysLoginInfoService;


}
