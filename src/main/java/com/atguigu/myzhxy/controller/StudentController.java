package com.atguigu.myzhxy.controller;

import com.atguigu.myzhxy.pojo.Student;
import com.atguigu.myzhxy.service.StudentService;
import com.atguigu.myzhxy.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//因为是异步交互，返回数据类型的方法，防止@ResponseBody的重复使用
@RequestMapping("/sms/studentController")//请求的运输路径，sms为本地controller层上
public class StudentController {
    //GET /sms/studentController/getStudentBy0pr/1/3?name=&clazzName=
    @Autowired
    private StudentService studentService;
    @ApiOperation("分页带条件模糊学生查询")
    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentByOpr(
            @ApiParam("分页查询页面") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("分页查询每页的条数")@PathVariable("pageSize") Integer pageSize,
            @ApiParam("分页查询的条件") Student student
    ){
        //分页信息封装
        Page<Student> page = new Page<>(pageNo,pageSize);
        IPage<Student> pageRs = studentService.getStudentByOpr(page,student);
        return Result.ok(pageRs);


    }
}
