package com.atguigu.zhxy.service;

import com.atguigu.zhxy.pojo.Clazz;
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
public interface ClazzService extends IService<Clazz> {

    IPage<Clazz> getClazzsByOpr(Page<Clazz> page, Clazz clazz);
}
