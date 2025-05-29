package com.dentalcare.g5.main.service.correo.impl;

import com.dentalcare.g5.main.dto.CorreoErrorDto;
import com.dentalcare.g5.main.service.correo.CorreoService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CorreoServiceImpl implements CorreoService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    @Override
    public void enviarCorreoError(CorreoErrorDto mensaje) {
        log.info("Enviando correo");
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(mensaje.getTo());
            helper.setTo(username);
            helper.setSubject(mensaje.getSubject());
            helper.setText(mensaje.getBody(), true);
            if(mensaje.getAttachmentName() != null  && !mensaje.getAttachmentName().isEmpty()){
                helper.addAttachment(mensaje.getAttachmentName(), mensaje.getAttachmentContent());
            }
            mailSender.send(mimeMessage);
            log.info("Correo enviado con exito");
        } catch (MessagingException e) {
            log.error("Error al enviar el correo {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
