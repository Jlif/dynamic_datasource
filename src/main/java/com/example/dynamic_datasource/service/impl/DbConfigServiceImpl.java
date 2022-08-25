package com.example.dynamic_datasource.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.dynamic_datasource.dao.DbConfigMapper;
import com.example.dynamic_datasource.entity.DbConfig;
import com.example.dynamic_datasource.service.DbConfigService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jiangchen
 * @since 2022-08-25
 */
@Service
public class DbConfigServiceImpl extends ServiceImpl<DbConfigMapper, DbConfig> implements DbConfigService {
}
