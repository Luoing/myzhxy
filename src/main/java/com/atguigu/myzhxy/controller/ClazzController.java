package com.atguigu.myzhxy.controller;

import com.atguigu.myzhxy.pojo.Clazz;
import com.atguigu.myzhxy.service.ClazzService;
import com.atguigu.myzhxy.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("班级管理器")
@RestController//因为是异步交互，返回数据类型的方法，防止@ResponseBody的重复使用
@RequestMapping("/sms/clazzController")//请求的运输路径，sms为本地controller层上
public class ClazzController {


    @Autowired
    private ClazzService clazzService;

    //sms/clazzController/deleteClazz
    @ApiOperation("删除班级管理")
    @DeleteMapping("/deleteClazz")
    public Result deleteClazz(@ApiParam("要删除的班级id的json集合")@RequestBody List<Integer> ids){

        clazzService.removeByIds(ids);
        return Result.ok();

    }

    @ApiOperation("增加或者修改班级信息")
    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(@ApiParam("json格式的班级信息")@RequestBody Clazz clazz){
        clazzService.saveOrUpdate(clazz);
        return Result.ok();
    }

    @ApiOperation("分页带条件班级模糊查询")
    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result getClazzByOpr(@ApiParam("分页查询页面") @PathVariable("pageNo") Integer pageNo, @ApiParam("分页查询每页的条数")@PathVariable("pageSize") Integer pageSize, @ApiParam("分页查询的条件")Clazz clazz){
        Page<Clazz> page = new Page<>(pageNo,pageSize);
        IPage<Clazz> pageRs = clazzService.getClazzsByOpr(page,clazz);
        return Result.ok(pageRs);
    }

}
