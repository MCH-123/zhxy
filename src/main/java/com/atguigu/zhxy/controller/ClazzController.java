package com.atguigu.zhxy.controller;


import com.atguigu.zhxy.pojo.Clazz;
import com.atguigu.zhxy.service.ClazzService;
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
 * 前端控制器
 * </p>
 *
 * @author mch
 * @since 2022-11-18
 */
@Api(tags = "班级控制器")
@RestController
@RequestMapping("sms/clazzController")
public class ClazzController {
    @Autowired
    private ClazzService clazzService;

    @ApiOperation("分页查询班级")
    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result getClazzsByOpr(
            @ApiParam("页码") @PathVariable Integer pageNo,
            @ApiParam("记录条数") @PathVariable Integer pageSize,
            @ApiParam("查询条件") Clazz clazz
    ) {
        //设置分页信息
        Page<Clazz> page = new Page<>(pageNo, pageSize);
        IPage<Clazz> iPage = clazzService.getClazzsByOpr(page, clazz);
        return Result.ok(iPage);
    }

    @ApiOperation("保存或修改班级信息")
    @PostMapping("saveOrupdateClazz")
    public Result saveOrupdateClazz(@ApiParam("clazz数据模型") @RequestBody Clazz clazz) {
        clazzService.saveOrUpdate(clazz);
        return Result.ok();
    }

    @ApiOperation("批量删除班级信息")
    @DeleteMapping("deleteClazz")
    public Result deleteClazz(@ApiParam("班级id集合") @RequestBody List<Integer> ids) {
        clazzService.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("获取所有班级")
    @GetMapping("getClazzs")
    public Result getClazzs() {
        List<Clazz> list = clazzService.list();
        return Result.ok(list);
    }
}

