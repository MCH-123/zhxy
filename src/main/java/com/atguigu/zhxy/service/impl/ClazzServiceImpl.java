package com.atguigu.zhxy.service.impl;

import com.atguigu.zhxy.pojo.Clazz;
import com.atguigu.zhxy.mapper.ClazzMapper;
import com.atguigu.zhxy.service.ClazzService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mch
 * @since 2022-11-18
 */
@Service
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {

    @Override
    public IPage<Clazz> getClazzsByOpr(Page<Clazz> page, Clazz clazz) {
        QueryWrapper<Clazz> qw = new QueryWrapper<>();
        if (clazz != null) {
            //年级名称条件
            String gradeName = clazz.getGradeName();
            if (!StringUtils.isEmpty(gradeName)) qw.eq("grade_name", gradeName);
            //班级名称条件
            String clazzName = clazz.getName();
            if (!StringUtils.isEmpty(clazzName)) qw.like("name", clazzName);
            qw.orderByDesc("id");
            qw.orderByAsc("name");
        }
            return baseMapper.selectPage(page, qw);
    }
}
