package com.atguigu.myzhxy.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//因为是异步交互，返回数据类型的方法，防止@ResponseBody的重复使用
@RequestMapping("/sms/gradeController")//请求的运输路径，sms为本地controller层上
public class GradeController {
}
