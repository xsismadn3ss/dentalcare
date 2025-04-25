package com.dentalcare.g5.main.model.payload.cita;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotaUpdateRequest {
    @NotNull(message = "El ID de la nota es obligatorio")
    private Integer id;
    
    private String titulo;
    private String descripcion;
}

