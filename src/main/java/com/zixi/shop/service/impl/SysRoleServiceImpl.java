package com.zixi.shop.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.zixi.shop.dao.SysRoleMapper;
import com.zixi.shop.entity.SysRole;
import com.zixi.shop.service.SysRoleService;


@Service("roleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {



}