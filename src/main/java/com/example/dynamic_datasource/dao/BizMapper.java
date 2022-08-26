package com.example.dynamic_datasource.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.dynamic_datasource.entity.Biz;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author jiangchen
 * @since 2022-08-25
 */
public interface BizMapper extends BaseMapper<Biz> {

    List<Biz> listBizCustom();

}
