package com.atguigu.zhxy.service.impl;

import com.atguigu.zhxy.mapper.StudentMapper;
import com.atguigu.zhxy.pojo.LoginForm;
import com.atguigu.zhxy.pojo.Student;
import com.atguigu.zhxy.service.StudentService;
import com.atguigu.zhxy.util.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    @Override
    public Student login(LoginForm loginForm) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        //拼接条件查询
        queryWrapper.eq("name", loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        //查询并返回
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<Student> getStudentByOpr(Page<Student> page, Student student) {
        //默认查询条件为空
        LambdaQueryWrapper<Student> qw = null;
        //有查询条件
        if (student != null) {
            qw = new LambdaQueryWrapper<>();
            //条件为班级名字
            if (student.getClazzName() != null) {
                qw.eq(Student::getClazzName, student.getClazzName());
            }
            //条件为姓名
            if (student.getName() != null) {
                qw.eq(Student::getName, student.getName());
            }
            //排序
            qw.orderByDesc(Student::getId).orderByAsc(Student::getName);
        }
        //返回分页数据
        return baseMapper.selectPage(page, qw);
    }
}
