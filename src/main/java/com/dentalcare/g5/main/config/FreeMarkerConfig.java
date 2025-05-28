package com.dentalcare.g5.main.config;

import freemarker.template.Configuration;
import org.springframework.context.annotation.Bean;

public class FreeMarkerConfig {
    @Bean
    public static Configuration freeMarkerConfig(){
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_31);
        configuration.setClassForTemplateLoading(FreeMarkerConfig.class, "/templates");
        configuration.setDefaultEncoding("UTF-8");
        return configuration;
    }
}
