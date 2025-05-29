package com.dentalcare.g5.main.config;

import com.dentalcare.g5.main.annotation.NotificarErrores;
import com.dentalcare.g5.main.dto.CorreoErrorDto;
import com.dentalcare.g5.main.service.correo.CorreoPlantillaService;
import com.dentalcare.g5.main.service.correo.CorreoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;

@Slf4j
@ControllerAdvice(annotations = NotificarErrores.class)
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final CorreoService correoService;
    private final CorreoPlantillaService correoPlantillaService;

    @Value("$sprin.mail.username")
    private String username;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex, HttpServletRequest request) {

        String plantilla = correoPlantillaService.crearPlantillaError(
                ex.getClass().getSimpleName(),
                ex.getMessage() + "ubicación:" + ex.getStackTrace()[0].getFileName() + " linea:" + ex.getStackTrace()[0].getLineNumber(),
                request.getRequestURL().toString()
        );

        CorreoErrorDto correo = new CorreoErrorDto();
        correo.setTo(username);
        correo.setSubject("⚠️ Error en la API DentalCare");
        correo.setBody(plantilla);
        correoService.enviarCorreoError(correo);
        log.info("Correo enviado con exito");
        return new ResponseEntity<>("Se ha producido un error interno. El equipo ha sido notificado.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
