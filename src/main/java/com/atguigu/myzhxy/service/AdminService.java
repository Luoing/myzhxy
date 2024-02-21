package com.atguigu.myzhxy.service;

import com.atguigu.myzhxy.pojo.Admin;
import com.atguigu.myzhxy.pojo.LoginForm;
import com.baomidou.mybatisplus.extension.service.IService;

public interface AdminService extends IService<Admin> {//针对Admin实现service层增删改查
    Admin login(LoginForm loginForm);
    
}
