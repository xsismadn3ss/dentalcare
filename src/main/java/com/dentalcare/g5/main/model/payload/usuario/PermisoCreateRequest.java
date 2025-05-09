package com.dentalcare.g5.main.model.payload.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermisoCreateRequest {
    private String nombre;
    private String descripcion;
}
