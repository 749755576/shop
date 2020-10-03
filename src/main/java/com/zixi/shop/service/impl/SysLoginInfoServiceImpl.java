package com.zixi.shop.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.zixi.shop.dao.SysLoginInfoMapper;
import com.zixi.shop.entity.SysInfo;
import com.zixi.shop.service.SysLoginInfoService;


@Service("loginInfoService")
public class SysLoginInfoServiceImpl extends ServiceImpl<SysLoginInfoMapper, SysInfo> implements SysLoginInfoService {


}