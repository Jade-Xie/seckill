package com.example.seckill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/demo")
public class DemoController {

    /**
     * 测试页面
     * @return
     */
    @RequestMapping("/hello")
    public String hello(Model model)
    {
        model.addAttribute("name", "xieys");
        return "hello";
    }
}
