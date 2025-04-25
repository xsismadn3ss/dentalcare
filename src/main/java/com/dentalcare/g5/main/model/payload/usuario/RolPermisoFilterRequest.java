package com.dentalcare.g5.main.model.payload.usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for filtering RolPermiso relationship entities
 * All fields are optional to allow flexible filtering criteria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolPermisoFilterRequest {
    private Integer id;
    private Integer rolId;
    private Integer permisoId;
}

