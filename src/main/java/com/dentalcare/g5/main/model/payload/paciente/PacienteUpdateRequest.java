package com.dentalcare.g5.main.model.payload.paciente;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteUpdateRequest {
    @NotNull(message = "El ID del paciente es obligatorio")
    private Integer id;
    
    private String nombre;
    private String apellido;
    private String telefono;
    private String direccion;
    private Integer usuario_id;
}

