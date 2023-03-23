//package com.snail.learn.controller.vilidator;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.InitBinder;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import javax.validation.Valid;
//
//@Controller
//@RequestMapping("/valid")
//public class UserController {
//    @Autowired
//    private UserValidator userValidator;
//
//    @InitBinder
//    private void initBinder(WebDataBinder binder) {
//        binder.addValidators(userValidator);
//    }
//
//    @RequestMapping(value = {"/index", "/", ""}, method = {RequestMethod.GET})
//    public String index(ModelMap m) {
//        m.addAttribute("user", new User());
//        return "user.jsp";
//    }
//
//    @RequestMapping(value = {"/signup"}, method = {RequestMethod.POST})
//    public String signup(@Valid User user, BindingResult br, RedirectAttributes redirectAttributes) {
//        redirectAttributes.addFlashAttribute("user", user);
//        return "user.jsp";
//    }
//}
