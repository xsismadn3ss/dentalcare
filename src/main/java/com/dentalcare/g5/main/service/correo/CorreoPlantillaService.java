package com.dentalcare.g5.main.service.correo;

import java.util.Optional;

public interface CorreoPlantillaService {
    String crearPlantillaError(String error_name, String details, String ubicacion, String url);
}
