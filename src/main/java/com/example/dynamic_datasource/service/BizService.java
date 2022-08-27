package com.example.dynamic_datasource.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dynamic_datasource.entity.Biz;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jiangchen
 * @since 2022-08-28
 */
public interface BizService extends IService<Biz> {

    Page<Biz> pageList();

}
