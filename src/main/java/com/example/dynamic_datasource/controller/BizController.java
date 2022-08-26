package com.example.dynamic_datasource.controller;

import com.example.dynamic_datasource.entity.Biz;
import com.example.dynamic_datasource.service.BizService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
        return bizService.listBiz();
    }

}
