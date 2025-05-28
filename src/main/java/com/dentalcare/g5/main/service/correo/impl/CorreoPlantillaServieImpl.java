package com.dentalcare.g5.main.service.correo.impl;

import com.dentalcare.g5.main.config.FreeMarkerConfig;
import com.dentalcare.g5.main.service.correo.CorreoPlantillaService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class CorreoPlantillaServieImpl implements CorreoPlantillaService {

    private final Configuration freemarkerConfiguration;

    @Value("${my.templade.default")
    String templateDefault;


    public CorreoPlantillaServieImpl(Configuration freemarkerConfiguration){
        this.freemarkerConfiguration = freemarkerConfiguration;
    }

    @Override
    public String crearPlantillaError(String error_name, String details) {
        try{
            Template template = freemarkerConfiguration.getTemplate(templateDefault);
            Map<String, Object> data = new HashMap<>();
            data.put("error_name", error_name);
            data.put("details", details);
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
        }catch (Exception e){
            log.error("Error al crear la plantilla: {}", e.getMessage());
        }
        return  "";
    }
}
