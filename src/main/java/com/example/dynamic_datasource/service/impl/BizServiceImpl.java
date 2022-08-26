package com.example.dynamic_datasource.service.impl;

import com.example.dynamic_datasource.dao.BizMapper;
import com.example.dynamic_datasource.entity.Biz;
import com.example.dynamic_datasource.service.BizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jiangchen
 * @since 2022-08-25
 */
@RequiredArgsConstructor
@Service
public class BizServiceImpl implements BizService {

    private final BizMapper bizMapper;

    @Override
    public List<Biz> listBiz() {
        return bizMapper.listBizCustom();
    }
}
