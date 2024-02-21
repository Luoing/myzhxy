package com.atguigu.myzhxy.mapper;

import com.atguigu.myzhxy.pojo.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository//方便spring识别接口
public interface AdminMapper extends BaseMapper<Admin> {//此接口由basemapper提供,并针对admin实体类进行泛形接口


}
