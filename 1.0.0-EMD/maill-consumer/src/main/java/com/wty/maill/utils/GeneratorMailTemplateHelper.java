package com.wty.maill.utils;

import com.wty.maill.entity.vo.MailDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;

@Component
public class GeneratorMailTemplateHelper {

    @Autowired
    private TemplateEngine templateEngine;

    public MailDataVO generatorTemplate(MailDataVO data){
        Context context = new Context();
        context.setLocale(Locale.CHINA);
        context.setVariables(data.getParams());
        String templateLocaltion = data.getTemplateName();
        String content = templateEngine.process(templateLocaltion, context);
        data.setContent(content);
        return data;
    }

}
