package com.dentalcare.g5.main.model.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolDto {
    private Integer id;
    private String nombre;
    private List<RolPermisoDto> rolPermisos;
    private UsuarioDto usuario;
}
