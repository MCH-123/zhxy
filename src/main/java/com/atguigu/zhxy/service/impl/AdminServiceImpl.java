package com.atguigu.zhxy.service.impl;

import com.atguigu.zhxy.pojo.Admin;
import com.atguigu.zhxy.mapper.AdminMapper;
import com.atguigu.zhxy.pojo.LoginForm;
import com.atguigu.zhxy.service.AdminService;
import com.atguigu.zhxy.util.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mch
 * @since 2022-11-18
 */
@Service
@Transactional
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    /**
     * 管理员登录
     * @param loginForm
     * @return
     */
    @Override
    public Admin login(LoginForm loginForm) {
        //创建查询对象
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        //拼接条件查询
        queryWrapper.eq("name", loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        //查询并返回
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<Admin> getAdmins(Page<Admin> page, String adminName) {
        LambdaQueryWrapper<Admin> qw = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(adminName)) qw.like(Admin::getName, adminName);
        qw.orderByDesc(Admin::getId).orderByAsc(Admin::getName);
        return baseMapper.selectPage(page, qw);
    }
}
