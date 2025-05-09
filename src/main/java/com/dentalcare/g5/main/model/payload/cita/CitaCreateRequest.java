package com.dentalcare.g5.main.model.payload.cita;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitaCreateRequest {
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;
    
    @NotNull(message = "La hora es obligatoria")
    private Time hora;
    
    private String motivo;
    
    @NotNull(message = "El ID del paciente es obligatorio")
    private Integer paciente_id;
    
    @NotNull(message = "El ID del doctor es obligatorio")
    private Integer doctor_id;
}

