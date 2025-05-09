package com.dentalcare.g5.main.model.payload.cita;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;

/**
 * DTO for filtering Cita entities
 * All fields are optional to allow flexible filtering criteria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CitaFilterRequest {
    private Integer id;
    private LocalDate fecha;
    private Time hora;
    private String motivo;
    private Integer doctor_id;
    private Integer paciente_id;
}

