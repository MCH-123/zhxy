package com.atguigu.zhxy.service.impl;

import com.atguigu.zhxy.pojo.Grade;
import com.atguigu.zhxy.mapper.GradeMapper;
import com.atguigu.zhxy.service.GradeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

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
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {

    @Override
    public IPage<Grade> getGradePageList(Page<Grade> page, String gradeName) {
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        // 设置查询条件
        if (!StringUtils.isEmpty(gradeName)) queryWrapper.like("name", gradeName);
        // 设置排序规则
        queryWrapper.orderByDesc("id");
        queryWrapper.orderByAsc("name");
        // 分页查询数据
        return baseMapper.selectPage(page, queryWrapper);
    }
}
