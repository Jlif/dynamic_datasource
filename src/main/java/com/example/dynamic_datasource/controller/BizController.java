package com.example.dynamic_datasource.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dynamic_datasource.entity.Biz;
import com.example.dynamic_datasource.service.BizService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jiangchen
 * @since 2022-08-25
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/biz")
public class BizController {

    private final BizService bizService;

    @GetMapping("/list")
    public List<Biz> list() {
        return bizService.list();
    }

    @GetMapping("/pages")
    public Page<Biz> pageList() {
        return bizService.pageList();
    }

    @Transactional(rollbackFor = Exception.class, transactionManager = "transactionManager")
    @PutMapping("/save")
    public void save() {
        Biz biz = new Biz();
        biz.setBiz("custom");
        bizService.save(biz);

        int i = 1 / 0;
    }

}
