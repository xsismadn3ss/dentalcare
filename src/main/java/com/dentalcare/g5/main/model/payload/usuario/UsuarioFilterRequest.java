package com.dentalcare.g5.main.model.payload.usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for filtering Usuario entities
 * All fields are optional to allow flexible filtering criteria
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioFilterRequest {
    private Integer id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private Integer rolId;
}

