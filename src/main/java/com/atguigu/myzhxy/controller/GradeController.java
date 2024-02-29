package com.atguigu.myzhxy.controller;

import com.atguigu.myzhxy.pojo.Grade;
import com.atguigu.myzhxy.service.GradeService;
import com.atguigu.myzhxy.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "年级控制器")//提供给swagger
@RestController//因为是异步交互，返回数据类型的方法，防止@ResponseBody的重复使用
@RequestMapping("/sms/gradeController")//请求的运输路径，sms为本地controller层上
public class GradeController {
    @Autowired
    private GradeService gradeService;

    @ApiOperation("获取全部年级")
    @GetMapping("/getGrades")
    public Result getGrades(){
        List<Grade> grades = gradeService.getGrades();
        return Result.ok(grades);
    }

    @ApiOperation("删除Grade信息")//面对swagger中相关的注解
    @DeleteMapping("/deleteGrade")//年级删除功能映射及功能添加
    public Result deleteGrade(@ApiParam("要删除的所有的grade的ID的Jason集合") @RequestBody List<Integer> ids){//apiparam是swagger接受的参数信息

        gradeService.removeByIds(ids);
        return Result.ok();
    }
    @ApiOperation("新增和修改grade，有id属性的则增加，没有则修改")
    @PostMapping("saveOrUpdateGrade")//增加功能
    public Result saveOrUpdateGrade(@ApiParam("json的grade对象") @RequestBody Grade grade){//因为请求的返回的是JASON格式，现在转换成grade来接受
        //接受参数
        //调用服务层方法增加或者修改
        gradeService.saveOrUpdate(grade);
        return Result.ok();
    }

    @ApiOperation("根据年级名称模糊查询和分页")
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    //本项目中返回的一切Jason对象都用result来获取，需要获得单独某个属性就放入result中
    public Result getGrades(@ApiParam("分页查询的页码数")@PathVariable("pageNo") Integer pageNo, @ApiParam("分页查询的条数")@PathVariable("pageSize") Integer pageSize, @ApiParam("分页查询的年级名称") String gradeName){
        //分页带条件查询
        Page<Grade> page = new Page<>(pageNo,pageSize);
        //通过服务层来查询
        IPage<Grade> pageRs = gradeService.getGradeByOpr(page,gradeName);
        //封装result对象返回
        return Result.ok(pageRs);
    }
}
