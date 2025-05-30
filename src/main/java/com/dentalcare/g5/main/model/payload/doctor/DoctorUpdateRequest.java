package com.dentalcare.g5.main.model.payload.doctor;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorUpdateRequest {
    private String nombre;
    private String apellido;
    private String telefono;
    private String no_vigiliancia;
    private Integer especialidad_id;
}

