package com.snail.resourceLoader;

import org.springframework.core.io.Resource;

import java.util.Map;

public class XMailer {
    private Resource template;

    public void sendMail(Map mailCtx) {
//        String mailContext = merge(getTemplate().getInputStream(), mailCtx);
    }

    public Resource getTemplate() {
        return template;
    }

    public void setTemplate(Resource template) {
        this.template = template;
    }
}
