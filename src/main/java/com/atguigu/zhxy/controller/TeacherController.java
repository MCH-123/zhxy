package com.atguigu.zhxy.controller;


import com.atguigu.zhxy.pojo.Student;
import com.atguigu.zhxy.pojo.Teacher;
import com.atguigu.zhxy.service.TeacherService;
import com.atguigu.zhxy.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author mch
 * @since 2022-11-18
 */
@Api(tags = "教师管理")
@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {
    @Resource
    private TeacherService teacherService;

    @ApiOperation("分页获取教师信息")
    @GetMapping("/getTeachers/{pageNo}/{pageSize}")
    public Result getTeachers(
            @ApiParam("页码") @PathVariable Integer pageNo,
            @ApiParam("记录条数") @PathVariable Integer pageSize,
            @ApiParam("查询条件") Teacher teacher
    ) {
        Page<Teacher> page = new Page<>(pageNo, pageSize);
        IPage<Teacher> iPage = teacherService.getTeachersByOpr(page, teacher);
        return Result.ok(iPage);
    }

    @ApiOperation("添加或修改教师信息")
    @PostMapping("saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(Teacher teacher) {
        teacherService.saveOrUpdate(teacher);
        return Result.ok();
    }

    @ApiOperation("批量删除教师信息")
    @DeleteMapping("deleteTeacher")
    public Result deleteTeacher(@ApiParam("教师id集合")List<Integer> ids) {
        teacherService.removeByIds(ids);
        return Result.ok();
    }
}

