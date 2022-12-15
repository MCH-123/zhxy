package com.atguigu.zhxy.controller;

import com.atguigu.zhxy.pojo.Admin;
import com.atguigu.zhxy.pojo.LoginForm;
import com.atguigu.zhxy.pojo.Student;
import com.atguigu.zhxy.pojo.Teacher;
import com.atguigu.zhxy.service.AdminService;
import com.atguigu.zhxy.service.StudentService;
import com.atguigu.zhxy.service.TeacherService;
import com.atguigu.zhxy.util.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author mch
 * @since 2022/11/18
 */
@Api(tags = "系统控制器")
@RestController
@RequestMapping("/sms/system")
public class SystemController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    @ApiOperation("获取图片验证码")
    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpSession session, HttpServletResponse response) {
        //获取验证码图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        //获取验证码字符串
        String verifiCode = String.valueOf(CreateVerifiCodeImage.getVerifiCode());
        //将验证码放入session
        session.setAttribute("verifiCode", verifiCode);

        try {
            //将图片响应给前端
            ImageIO.write(verifiCodeImage, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation("登录验证")
    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm, HttpSession session) {
        //获取用户提交的验证码和session中的验证码
        String systemVerifiCode = (String) session.getAttribute("verifiCode");
        String loginVerifiCode = loginForm.getVerifiCode();
        if ("".equals(systemVerifiCode)) return Result.fail().message("验证码失效,请刷新后重试!");
        if (!loginVerifiCode.equalsIgnoreCase(systemVerifiCode)) return Result.fail().message("验证码有误,请刷新后重新输入!");
        //移除验证码
        session.removeAttribute("verifiCode");
        //存放响应信息
        Map<String, Object> map = new HashMap<>();

        //根据用户身份验证登录用户信息
        switch (loginForm.getUserType()) {
            case 1: //管理员
                try {
                    Admin login  = adminService.login(loginForm);
                    if (null != login) {
                        //登陆成功,将用户id和用户类型转换为token口令,作为信息响应给前端
                        map.put("token", JwtHelper.createToken(login.getId().longValue(), 1));
                    } else {
                        throw new RuntimeException("用户名或密码错误!");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    // 捕获异常,向用户响应错误信息
                    return Result.fail().message(e.getMessage());
                }
            case 2:    //学生
                try {
                    Student login = studentService.login(loginForm);
                    if (login != null) {
                        map.put("token", JwtHelper.createToken(login.getId().longValue(), 2));
                    } else {
                        throw new RuntimeException("用户名或密码错误!");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 3: //教师
                try {
                    Teacher login = teacherService.login(loginForm);
                    if (login != null) {
                        map.put("token", JwtHelper.createToken(login.getId().longValue(), 3));
                    } else {
                        throw new RuntimeException("用户名或密码错误!");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
        }
        // 查不到用户 响应失败
        return Result.fail().message("此用户不存在");
    }

    @ApiOperation("通过token获取用户信息")
    @GetMapping("/getInfo")
    public Result getUserInfoByToken(@RequestHeader("token") String token) {
        // 检查token是否过期
        boolean isEx = JwtHelper.isExpiration(token);
        if (isEx) return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        // 解析token
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        //存放响应数据
        HashMap<String, Object> map = new HashMap<>();
        Assert.notNull(userType);
        switch (userType) {
            case 1:
                Admin admin = adminService.getById(userId);
                map.put("user", admin);
                map.put("userType", 1);
                break;
            case 2:
                Student student = studentService.getById(userId);
                map.put("user", student);
                map.put("userType", 2);
                break;
            case 3:
                Teacher teacher = teacherService.getById(userId);
                map.put("user", teacher);
                map.put("userType", 3);
                break;
        }
        return Result.ok(map);
    }

    @ApiOperation("头像上传")
    @PostMapping("headerImgUpload")
    public Result headerImgUpload(
            @ApiParam("文件") @RequestPart("multipartFile") MultipartFile multipartFile
    ) {
        //UUID生成文件随机名
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        //生成新文件名
        String fileName = uuid.concat(multipartFile.getOriginalFilename());
        //文件保存路径(填写自己的文件地址)
        String portraitPath = "F:\\尚硅谷上海课程\\智慧校园\\zhxy\\target\\classes\\public\\upload/".concat(fileName);
        //保存文件
        try {
            multipartFile.transferTo(new File(portraitPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String headerImg = "upload/" + fileName;
        return Result.ok(headerImg);
    }

    @ApiOperation("修改密码")
    @PostMapping("/updatePwd/{oldPwd}/{newPwd}")
    public Result updatePwd(
            @ApiParam("token")
            @RequestHeader("token") String token,
            @ApiParam("旧密码")
            @PathVariable("oldPwd") String oldPwd,
            @ApiParam("新密码")
            @PathVariable("newPwd") String newPwd
    ) {
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration) return Result.fail().message("token失效");
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        //转成密文
        oldPwd = MD5.encrypt(oldPwd);
        newPwd = MD5.encrypt(newPwd);
        assert userType != null;
        if (userType == 1) {
            LambdaQueryWrapper<Admin> qw = new LambdaQueryWrapper<>();
            assert userId != null;
            qw.eq(Admin::getId, userId.intValue()).eq(Admin::getPassword, oldPwd);
            Admin admin = adminService.getOne(qw);
            if (admin != null) {
                admin.setPassword(newPwd);
                adminService.saveOrUpdate(admin);
            } else {
                return Result.fail().message("原密码输入有误!");
            }

        } else if (userType == 2){
            LambdaQueryWrapper<Student> qw = new LambdaQueryWrapper<>();
            assert userId != null;
            qw.eq(Student::getId, userId.intValue()).eq(Student::getPassword, oldPwd);
            Student student = studentService.getOne(qw);
            if (student != null) {
                student.setPassword(newPwd);
                studentService.saveOrUpdate(student);
            } else {
                return Result.fail().message("原密码输入有误!");
            }
        }else if (userType == 3){
            LambdaQueryWrapper<Teacher> qw = new LambdaQueryWrapper<>();
            assert userId != null;
            qw.eq(Teacher::getId, userId.intValue()).eq(Teacher::getPassword, oldPwd);
            Teacher teacher = teacherService.getOne(qw);
            if (teacher != null) {
                teacher.setPassword(newPwd);
                teacherService.saveOrUpdate(teacher);
            } else {
                return Result.fail().message("原密码输入有误!");
            }
        }
        return Result.ok();
    }
}
