package com.atguigu.zhxy.service;

import com.atguigu.zhxy.pojo.Grade;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mch
 * @since 2022-11-18
 */
public interface GradeService extends IService<Grade> {
    // 查询分页
    IPage<Grade> getGradePageList(Page<Grade> page, String gradeName);

}
