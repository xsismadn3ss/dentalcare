package com.dentalcare.g5.main.model.payload.doctor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for filtering Doctor entities
 * All fields are optional to allow flexible filtering criteria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorFilterRequest {
    private Integer id;
    private Integer especialidad_id;
    private Integer usuario_id;
}

