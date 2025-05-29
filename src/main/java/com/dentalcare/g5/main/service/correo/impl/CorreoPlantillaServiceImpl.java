package com.dentalcare.g5.main.service.correo.impl;

import com.dentalcare.g5.main.service.correo.CorreoPlantillaService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class CorreoPlantillaServiceImpl implements CorreoPlantillaService {

    private final Configuration freemarkerConfiguration;

    @Value("${my.template.default}")
    String templateDefault;

    public CorreoPlantillaServiceImpl(Configuration freemarkerConfiguration) {
        this.freemarkerConfiguration = freemarkerConfiguration;
    }

    @Override
    public String crearPlantillaError(String error_name, String details, String ubicacion, String url) {
        try {
            Template template = freemarkerConfiguration.getTemplate(templateDefault);
            Map<String, Object> data = new HashMap<>();
            data.put("error_name", error_name != null ? error_name : "Error Desconocido");
            data.put("details", details != null ? details : "Sin detalles disponibles");
            data.put("ubicacion", ubicacion != null ? ubicacion: "No localizado");
            data.put("url", url != null ? url : "URL Desconocida");
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
        } catch (Exception e) {
            log.error("Error al crear la plantilla: {}", e.getMessage());
            return "";
        }
    }
}