package com.atguigu.zhxy.service;

import com.atguigu.zhxy.pojo.LoginForm;
import com.atguigu.zhxy.pojo.Teacher;
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
public interface TeacherService extends IService<Teacher> {
    /**
     * 教师登录
     * @param loginForm
     * @return
     */
    Teacher login(LoginForm loginForm);

    IPage<Teacher> getTeachersByOpr(Page<Teacher> page, Teacher teacher);
}
