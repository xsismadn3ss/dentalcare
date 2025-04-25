package com.dentalcare.g5.main.model.payload.paciente;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO for filtering Paciente entities
 * All fields are optional to allow flexible filtering criteria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PacienteFilterRequest {
    private Integer id;
    private LocalDate fechaRegistroDesde;
    private LocalDate fechaRegistroHasta;
    private Integer usuarioId;
    
    // For filtering by usuario attributes
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
}

