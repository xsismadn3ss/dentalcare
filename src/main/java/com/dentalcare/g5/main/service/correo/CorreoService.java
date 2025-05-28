package com.dentalcare.g5.main.service.correo;

import com.dentalcare.g5.main.dto.CorreoErrorDto;

public interface CorreoService {
    void enviarCorreoError(CorreoErrorDto mensaje);
}
