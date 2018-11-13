package com.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/hello")
public class HelloSpringController {
    String message = "Welcome to Spring MVC!"; 
 
    @RequestMapping(value="/show")
    public ModelAndView showMessage(@RequestParam(value = "name", required = false, defaultValue = "Spring") String name) {
 
        ModelAndView mv = new ModelAndView();//指定视图
        //向视图中添加所要展示或使用的内容，将在页面中使用
        mv.setViewName("/static/jsp/hellospring");
        mv.addObject("message", message);
        mv.addObject("name", name);
        return mv;
    }
}
