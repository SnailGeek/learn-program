package com.lagou.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @RequestMapping("/handle01")
    public ModelAndView handle01() {
        // 服务器时间
        Date date = new Date();
        // 返回服务器时间到前端页面
        // 封装了数据和页面信息的modelandview
        ModelAndView modelAndView = new ModelAndView();
        // 向请求域中放入request.setAttribute("date", date)
        modelAndView.addObject("date", date);
        // 跳转页面,配置了视图解析器
        modelAndView.setViewName("success");
        return modelAndView;
    }

    /*
     * SpringMVC在handler方法上传入Map、Model和Modelmap参数并向这些参数
     *运行时具体类型都是BindingAwareModelMap  相当于给它中保存的数据都是在请求域中
     *
     * Map:jdk中的接口
     * Model: Spring框架接口
     * ModelMap: 继承了LinkedHashMap，Map的实现
     * BindingAwareModelMap 继承了ModelMap，实现了Model接口
     * */

    @RequestMapping("/handle11")
    public String handle11(ModelMap modelMap) {
        // 服务器时间
        Date date = new Date();
        modelMap.addAttribute("date", date);
        System.out.println("===========modelMap" + modelMap.getClass());
        return "success";
    }

    /*
     *直接声明形参Model，封装数据
     * */
    @RequestMapping("/handle12")
    public String handle12(Model model) {
        // 服务器时间
        Date date = new Date();
        model.addAttribute("date", date);
        System.out.println("===========model" + model.getClass());
        return "success";
    }

    @RequestMapping("/handle13")
    public String handle12(Map<String, Object> map) {
        // 服务器时间
        Date date = new Date();
        map.put("date", date);
        System.out.println("===========Map" + map.getClass());
        return "success";
    }
}
