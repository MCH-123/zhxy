package com.atguigu.zhxy.controller;


import com.atguigu.zhxy.pojo.Admin;
import com.atguigu.zhxy.pojo.Teacher;
import com.atguigu.zhxy.service.AdminService;
import com.atguigu.zhxy.util.MD5;
import com.atguigu.zhxy.util.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
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
@Api(tags = "管理员管理")
@RestController
@RequestMapping("/sms/adminController")
public class AdminController {
    @Resource
    private AdminService adminService;

    @ApiOperation("分页获取管理员信息")
    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(
            @ApiParam("页码") @PathVariable Integer pageNo,
            @ApiParam("记录条数") @PathVariable Integer pageSize,
            @ApiParam("查询条件") String adminName
    ) {
        Page<Admin> page = new Page<>(pageNo, pageSize);
        IPage<Admin> iPage = adminService.getAdmins(page, adminName);
        return Result.ok(iPage);
    }

    @ApiOperation("添加或修改管理员信息")
    @PostMapping("saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(Admin admin) {
        //加密
        if (!StringUtils.isEmpty(admin.getPassword())) admin.setPassword(MD5.encrypt(admin.getPassword()));
        adminService.saveOrUpdate(admin);
        return Result.ok();
    }

    @ApiOperation("批量删除管理员信息")
    @DeleteMapping("deleteAdmin")
    public Result deleteAdmin(@ApiParam("管理员id集合") @RequestBody List<Integer> ids) {
        adminService.removeByIds(ids);
        return Result.ok();
    }

}

