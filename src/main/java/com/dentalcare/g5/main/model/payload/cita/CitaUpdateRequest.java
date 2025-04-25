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
public class CitaUpdateRequest {
    @NotNull(message = "El ID de la cita es obligatorio")
    private Integer id;
    
    private LocalDate fecha;
    private Time hora;
    private String motivo;
}

