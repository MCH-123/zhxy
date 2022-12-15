package com.atguigu.zhxy.service.impl;

import com.atguigu.zhxy.pojo.Admin;
import com.atguigu.zhxy.pojo.LoginForm;
import com.atguigu.zhxy.pojo.Teacher;
import com.atguigu.zhxy.mapper.TeacherMapper;
import com.atguigu.zhxy.service.TeacherService;
import com.atguigu.zhxy.util.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author mch
 * @since 2022-11-18
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Override
    public Teacher login(LoginForm loginForm) {
        //创建查询对象
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        //拼接条件查询
        queryWrapper.eq("name", loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        //查询并返回
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<Teacher> getTeachersByOpr(Page<Teacher> page, Teacher teacher) {

        LambdaQueryWrapper<Teacher> qw = new LambdaQueryWrapper<>();
        if (teacher != null) {
            //班级名称条件
            if (!StringUtils.isEmpty(teacher.getClazzName())) qw.eq(Teacher::getClazzName, teacher.getClazzName());
            //教师名称条件
            if(!StringUtils.isEmpty(teacher.getName())) qw.eq(Teacher::getName, teacher.getName());
            qw.orderByDesc(Teacher::getId).orderByAsc(Teacher::getName);
        }
        return baseMapper.selectPage(page, qw);
    }
}
