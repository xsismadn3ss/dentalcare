package com.dentalcare.g5.main.model.payload.cita;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotaCreateRequest {
    @NotBlank(message = "El título es obligatorio")
    private String titulo;
    
    private String descripcion;
    
    @NotNull(message = "El ID de la cita es obligatorio")
    private Integer cita_id;
}

