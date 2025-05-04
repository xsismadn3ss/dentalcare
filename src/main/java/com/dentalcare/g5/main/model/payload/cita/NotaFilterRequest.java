package com.dentalcare.g5.main.model.payload.cita;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for filtering Nota entities
 * All fields are optional to allow flexible filtering criteria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotaFilterRequest {
    private Integer id;
    private String titulo;
    private String descripcion;
    private Integer cita_id;
}

