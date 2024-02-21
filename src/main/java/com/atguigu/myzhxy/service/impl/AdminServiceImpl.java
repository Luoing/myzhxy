package com.atguigu.myzhxy.service.impl;

import com.atguigu.myzhxy.mapper.AdminMapper;
import com.atguigu.myzhxy.pojo.Admin;
import com.atguigu.myzhxy.pojo.LoginForm;
import com.atguigu.myzhxy.service.AdminService;
import com.atguigu.myzhxy.util.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("adminServiceImpl")//初始化service层，初始化serviceimpl，在调用这个实体类的时候可以调用adminServiceImpl这个自己定义的id
@Transactional//事务控制，可以开始使用serviceimpl
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {//实现类的创建
    @Override
    public Admin login(LoginForm loginForm) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();//mybatius判断条件
        queryWrapper.eq("name",loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));//转换成MD5加密格式进行比对
        Admin admin = baseMapper.selectOne(queryWrapper);//验证通过返回基本信息

        return admin;
    }

    @Override
    public Admin getAdminById(Long userId) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<Admin>();
        queryWrapper.eq("id",userId);
        Admin admin = baseMapper.selectOne(queryWrapper);
        return admin;
    }
}
