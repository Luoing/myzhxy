package com.atguigu.myzhxy.controller;

import com.atguigu.myzhxy.pojo.Grade;
import com.atguigu.myzhxy.service.GradeService;
import com.atguigu.myzhxy.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController//因为是异步交互，返回数据类型的方法，防止@ResponseBody的重复使用
@RequestMapping("/sms/gradeController")//请求的运输路径，sms为本地controller层上
public class GradeController {
    @Autowired
    private GradeService gradeService;

    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    //本项目中返回的一切Jason对象都用result来获取，需要获得单独某个属性就放入result中
    public Result getGrades(@PathVariable("pageNo") Integer pageNo, @PathVariable("pageSize") Integer pageSize, String gradeName){
        //分页带条件查询
        Page<Grade> page = new Page<>(pageNo,pageSize);
        //通过服务层来查询
        IPage<Grade> pageRs = gradeService.getGradeByOpr(page,gradeName);
        //封装result对象返回
        return Result.ok(pageRs);
    }
}
