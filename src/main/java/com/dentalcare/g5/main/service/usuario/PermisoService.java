package com.dentalcare.g5.main.service.usuario;

import com.dentalcare.g5.main.model.dto.usuario.PermisoDto;
import com.dentalcare.g5.main.model.dto.usuario.RolPermisoDto;
import com.dentalcare.g5.main.model.payload.usuario.PermisoCreateRequest;
import com.dentalcare.g5.main.model.payload.usuario.PermisoFilterRequest;
import com.dentalcare.g5.main.model.payload.usuario.PermisoUpdateRequest;

import java.util.List;

/**
 * Service interface for managing permisos (permissions)
 */

public interface PermisoService {
    PermisoDto addPermiso(PermisoCreateRequest payload);
    PermisoDto updatePermiso(PermisoUpdateRequest paylodad, Integer id);
    PermisoDto getPermisoById(int id);
    List<PermisoDto> getAllPermisos();
    List<PermisoDto> filterPermisos(PermisoFilterRequest payload);
    void deletePermiso(int id);
    List<RolPermisoDto> joinRolPermiso(int id);
}

