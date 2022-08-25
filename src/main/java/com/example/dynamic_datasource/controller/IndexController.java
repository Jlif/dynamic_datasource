package com.example.dynamic_datasource.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jiangchen
 * @date 2020/08/04
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String demo() {
        return "index";
    }

}
