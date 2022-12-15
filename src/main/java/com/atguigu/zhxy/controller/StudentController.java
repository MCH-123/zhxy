package com.atguigu.zhxy.controller;


import com.atguigu.zhxy.pojo.Clazz;
import com.atguigu.zhxy.pojo.Student;
import com.atguigu.zhxy.service.StudentService;
import com.atguigu.zhxy.util.MD5;
import com.atguigu.zhxy.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author mch
 * @since 2022-11-18
 */
@Api(tags = "学生控制器")
@RestController
@RequestMapping("/sms/studentController")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @ApiOperation("分页查询学生信息")
    @GetMapping("getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentByOpr(
            @ApiParam("页码") @PathVariable Integer pageNo,
            @ApiParam("记录条数") @PathVariable Integer pageSize,
            @ApiParam("查询条件") Student student
    ) {
        Page<Student> page = new Page<>(pageNo, pageSize);
        IPage<Student> iPage = studentService.getStudentByOpr(page, student);
        return Result.ok(iPage);
    }

    @ApiOperation("增加学生信息")
    @PostMapping("addOrUpdateStudent")
    public Result addOrUpdateStudent(@RequestBody Student student) {
        //对学生密码加密
        if (!Strings.isEmpty(student.getPassword())) {
            student.setPassword(MD5.encrypt(student.getPassword()));
        }
        //保存学生信息
        studentService.saveOrUpdate(student);
        return Result.ok();
    }

    @ApiOperation("批量删除学生信息")
    @DeleteMapping("delStudentById")
    public Result delStudentById(
            @ApiParam("多个学生id集合") @RequestBody List<Integer> ids
            ) {
        studentService.removeByIds(ids);
        return Result.ok();
    }
}

