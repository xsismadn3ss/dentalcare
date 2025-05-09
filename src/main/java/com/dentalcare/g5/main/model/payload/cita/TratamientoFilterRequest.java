package com.dentalcare.g5.main.model.payload.cita;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for filtering Tratamiento entities
 * All fields are optional to allow flexible filtering criteria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TratamientoFilterRequest {
    private Integer id;
    private String nombre;
    private Boolean pendiente;
    private Integer cita_id;
}

