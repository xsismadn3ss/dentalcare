package com.dentalcare.g5.main.model.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolPermisoDto {
    private  Integer id;
    private Integer permiso_id;
    private Integer rol_id;
}
