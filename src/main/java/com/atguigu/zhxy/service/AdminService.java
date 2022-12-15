package com.atguigu.zhxy.service;

import com.atguigu.zhxy.pojo.Admin;
import com.atguigu.zhxy.pojo.LoginForm;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mch
 * @since 2022-11-18
 */
public interface AdminService extends IService<Admin> {
    /**
     * 登录
     * @param loginForm
     * @return
     */
    Admin login(LoginForm loginForm);

    IPage<Admin> getAdmins(Page<Admin> page, String adminName);
}
