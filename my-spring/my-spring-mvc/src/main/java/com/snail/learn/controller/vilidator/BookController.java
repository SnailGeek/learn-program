package com.snail.learn.controller.vilidator;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/book")
@SessionAttributes(value = {"book", "description"}, types = {Double.class})
public class BookController {
    private final Log logger = LogFactory.getLog(BookController.class);

    @RequestMapping("/index")
    public String index(Model model) throws Exception {
        model.addAttribute("book", "金刚经");
        model.addAttribute("description", "般若系列经典");
        model.addAttribute("price", new Double("9999.9"));
        return "redirect:get";
    }

    @RequestMapping("/get")
    public String getBySessionAttributes(@ModelAttribute("book") String book, ModelMap model, SessionStatus sessionStatus) {
        logger.info("=================getBySessionAttributes=================");
        logger.info("get by @ModelAttribute: " + book);
        logger.info("get by ModelMap: " + model.get("book") + ": " + model.get("description") + ", " + model.get("price"));
        sessionStatus.setComplete();
        return "redirect:complete";
    }

    @RequestMapping("/complete")
    public String afterComplete(ModelMap model) throws Exception {
        logger.info("======================afterComplete========================");
        logger.info(model.get("book") + ": " + model.get("description") + ", " + model.get("price"));
        return "index";
    }
}
