package com.dentalcare.g5.main.model.payload.cita;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TratamientoUpdateRequest {
    @NotNull(message = "El ID del tratamiento es obligatorio")
    private Integer id;
    
    private String nombre;
    private Boolean pendiente;
}

