package com.dentalcare.g5.main.service.usuario;

import com.dentalcare.g5.main.model.dto.usuario.RolPermisoDto;
import com.dentalcare.g5.main.model.payload.usuario.RolPermisoFilterRequest;

import java.util.List;

/**
 * Service interface for managing rol-permiso relationships
 */

public interface RolPermisoService {
    RolPermisoDto addRolPer(RolPermisoDto rolPermisoDto);
    RolPermisoDto updateRolPer(RolPermisoDto rolPermisoDto);
    RolPermisoDto getRolPerById(int id);
    List<RolPermisoDto> getAllRolPer();
    List<RolPermisoDto> filterRolPer(RolPermisoFilterRequest payload);
    void deleteRolPer(int id);
}



