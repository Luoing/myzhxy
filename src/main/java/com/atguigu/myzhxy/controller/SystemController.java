package com.atguigu.myzhxy.controller;
//针对对非表格进行的CRUD的操作，一些公共的功能在此类中放入，比如验证码的实现

import com.atguigu.myzhxy.pojo.Admin;
import com.atguigu.myzhxy.pojo.LoginForm;
import com.atguigu.myzhxy.pojo.Student;
import com.atguigu.myzhxy.pojo.Teacher;
import com.atguigu.myzhxy.service.AdminService;
import com.atguigu.myzhxy.service.StudentService;
import com.atguigu.myzhxy.service.TeacherService;
import com.atguigu.myzhxy.util.CreateVerifiCodeImage;
import com.atguigu.myzhxy.util.JwtHelper;
import com.atguigu.myzhxy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController//因为是异步交互，返回数据类型的方法，防止@ResponseBody的重复使用
@RequestMapping("/sms/system")//请求的运输路径，sms为本地controller层上
public class SystemController {
    @Autowired//是Spring框架中的一个注解，它用于自动装配bean的依赖关系。这个注解可以应用于类成员变量、方法及构造函数上，以告诉Spring框架自动完成这些依赖关系的注入。
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    //校验登陆是否成功的方法
    @PostMapping("/login")//此为一开始的post校验请求
    public Result login(@RequestBody LoginForm loginForm,HttpServletRequest request){//使用result是因为返回的对象是code、message等需要result来转换成Jason对象
                                                          //需要使用@RequestBody来转换成jason格式,request来获取session对象

        //验证码校验
        HttpSession session = request.getSession();//获取存着的session对象
        String sessionVerifiCode = (String)session.getAttribute("verifiCode");//获取到session里面存着的验证码转换成字符串
        String loginFormVerifiCode = loginForm.getVerifiCode();//获取到实体类中loginForm中的验证信息
        if ( "".equals(sessionVerifiCode) || null == sessionVerifiCode) {//判断验证失效时间太长
            return Result.fail().message("验证码失效，请刷新后重试！");
        }
        if (!sessionVerifiCode.equalsIgnoreCase(loginFormVerifiCode)){//使用equalsIgnoreCase()方法可以在不区分大小写的情况下比较两个字符串是否相等
            return Result.fail().message("验证码错误！");
        }
        //从session中移除响应的验证的验证码
        session.removeAttribute("verifiCode");
        //分用户类型进行校验
        Map<String,Object> map = new LinkedHashMap<>();
        switch (loginForm.getUserType()){//通过页面被选择的角色获取到usertype,进行角色切换
            case 1://管理员角色
                try {
                    Admin admin = adminService.login(loginForm);//通过service层获取数据库信息返回到实体类Admin中的admin
                    if (null != admin){
                        //用户的ID和类型转换成一个密文，以token的名称向客户端反馈
                        String token = JwtHelper.createToken(admin.getId().longValue(), 1);
                        map.put("token",token);
                    }else {
                        throw  new RuntimeException("用户名或者密码错误！");
                    }
                    return Result.ok(map);//正确登陆返回
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());//异常错误信息返回
                }
            case 2://学生角色
                try {
                    Student student = studentService.login(loginForm);//通过service层获取数据库信息返回到实体类Admin中的admin
                    if (null != student){
                        //用户的ID和类型转换成一个密文，以token的名称向客户端反馈
                        String token = JwtHelper.createToken(student.getId().longValue(), 2);
                        map.put("token",token);
                    }else {
                        throw  new RuntimeException("用户名或者密码错误！");
                    }
                    return Result.ok(map);//正确登陆返回
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());//异常错误信息返回
                }
            case 3:
                try {
                    Teacher teacher = teacherService.login(loginForm);//通过service层获取数据库信息返回到实体类Admin中的admin
                    if (null != teacher){
                        //用户的ID和类型转换成一个密文，以token的名称向客户端反馈
                        String token = JwtHelper.createToken(teacher.getId().longValue(), 3);
                        map.put("token",token);
                    }else {
                        throw  new RuntimeException("用户名或者密码错误！");
                    }
                    return Result.ok(map);//正确登陆返回
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());//异常错误信息返回
                }


        }

        return Result.fail().message("查无此用户");




    }


    @RequestMapping("/getVerifiCodeImage")//任意请求都可实现
//    @GetMapping("/getVerifiCodeImage")//请求唯一
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response) throws IOException {//使用request来获取session对象,用respond来响应获取到的对象
        //获取图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        //获取图片上的验证码
        String verifiCode =new String( CreateVerifiCodeImage.getVerifiCode());//原本是char类型，强转换为String类型
        //将验证码文本放入session域内，为下一次验证做准备(将verificode放入Session域中)
        HttpSession session = request.getSession();//获取到session
        session.setAttribute("verifiCode",verifiCode);//往session域中设置
        //将验证码图片响应给浏览器
        ServletOutputStream outputStream = response.getOutputStream();//获取到输出流
        ImageIO.write(verifiCodeImage,"JPEG",outputStream);//通过ImageIO的IO流实现验证图片的方法，以JPEG格式来写入流中

    }

}
