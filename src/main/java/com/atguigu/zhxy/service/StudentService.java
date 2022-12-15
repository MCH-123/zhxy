package com.atguigu.zhxy.service;

import com.atguigu.zhxy.pojo.LoginForm;
import com.atguigu.zhxy.pojo.Student;
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
public interface StudentService extends IService<Student> {
    /**
     * 学生登录
     * @param loginForm
     * @return
     */
    Student login(LoginForm loginForm);

    IPage<Student> getStudentByOpr(Page<Student> page, Student student);
}
