package com.templateproject.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class ThymeleafConfig {

    private SpringTemplateEngine templateEngine;

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.addTemplateResolver(htmlTemplateResolver());
        return engine;
    }

    private ITemplateResolver htmlTemplateResolver() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setSuffix(".html");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setOrder(1);
        resolver.setCacheable(false);
        return resolver;
    }
}


