package com.dentalcare.g5.main.model.payload.cita;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TratamientoCreateRequest {
    @NotBlank(message = "El nombre del tratamiento es obligatorio")
    private String nombre;
    
    private Boolean pendiente = true;
    
    @NotNull(message = "El ID de la cita es obligatorio")
    private Integer citaId;
}

