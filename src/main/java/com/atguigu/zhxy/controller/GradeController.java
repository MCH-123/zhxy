package com.atguigu.zhxy.controller;


import com.atguigu.zhxy.pojo.Grade;
import com.atguigu.zhxy.service.GradeService;
import com.atguigu.zhxy.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mch
 * @since 2022-11-18
 */
@Api(tags = "年级控制器")
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {
    @Autowired
    private GradeService gradeService;

    @ApiOperation("分页查询年级信息")
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGradeByOpr(
            @ApiParam("页码") @PathVariable Integer pageNo,
            @ApiParam("记录条数") @PathVariable Integer pageSize,
            @ApiParam("查询条件") String gradeName
    ) {
        //设置分页信息
        Page<Grade> page = new Page<>(pageNo,pageSize);
        IPage<Grade> pageRs = gradeService.getGradePageList(page, gradeName);
        return Result.ok(pageRs);
    }

    @ApiOperation("添加或修改年级信息")
    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(
            @ApiParam("年级") @RequestBody Grade grade
    ) {
        gradeService.saveOrUpdate(grade);
        return Result.ok();
    }

    @ApiOperation("删除一或多个年级信息")
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(
            @ApiParam("年级id集合")
            @RequestBody List<Integer> ids
    ) {
        gradeService.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("获取所有年级信息")
    @GetMapping("/getGrades")
    public Result getGrades() {
        List<Grade> grades = gradeService.getBaseMapper().selectList(null);
        return Result.ok(grades);
    }

}

