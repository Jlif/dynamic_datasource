package com.example.dynamic_datasource.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dynamic_datasource.dao.BizMapper;
import com.example.dynamic_datasource.entity.Biz;
import com.example.dynamic_datasource.service.BizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jiangchen
 * @since 2022-08-28
 */
@RequiredArgsConstructor
@Service
public class BizServiceImpl extends ServiceImpl<BizMapper, Biz> implements BizService {

    private final BizMapper bizMapper;

    @Override
    public Page<Biz> pageList() {
        return bizMapper.selectPage(Page.of(1, 10), Wrappers.emptyWrapper());
    }
}
