package com.dentalcare.g5.main.service.usuario;

import com.dentalcare.g5.main.model.dto.usuario.RolPermisoDto;
import com.dentalcare.g5.main.model.payload.usuario.RolPermisoFilterRequest;

import java.util.List;

/**
 * Service interface for managing rol-permiso relationships
 */
public interface RolPermisoService {
    RolPermisoDto addRolPermiso(RolPermisoDto rolPermisoDto);
    RolPermisoDto updateRolPermiso(RolPermisoDto rolPermisoDto);
    RolPermisoDto getRolPermisoById(int id);
    List<RolPermisoDto> getAllRolPermisos();
    List<RolPermisoDto> filterRolPermisos(RolPermisoFilterRequest payload);
    void deleteRolPermiso(int id);
}

