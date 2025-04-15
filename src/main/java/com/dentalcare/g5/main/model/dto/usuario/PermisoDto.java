package com.dentalcare.g5.main.model.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermisoDto {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Boolean activo;
    private List<RolPermisoDto> rolpermisos;
}
