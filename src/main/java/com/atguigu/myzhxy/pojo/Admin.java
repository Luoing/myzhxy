package com.atguigu.myzhxy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data//get、set、tostring方法全部生成
@AllArgsConstructor//全参构造方法
@NoArgsConstructor//无参构造方法
@TableName("tb_admin")//mybatisplus讲实体与数据库形成映射，一一进行绑定
public class Admin {//
    @TableId(value = "id",type = IdType.AUTO)//对主键进行标记
    private Integer id;
    private String name;
    private char gender;
    private String password;
    private String email;
    private String telephone;
    private String address;
    private String portraitPath;//
}
