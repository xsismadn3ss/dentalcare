package com.dentalcare.g5.main.model.payload.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermisoUpdateRequest {
    private String nombre;
    private String descripcion;
    private Boolean activo;
}
