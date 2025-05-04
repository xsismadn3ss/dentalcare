package com.dentalcare.g5.main.service.usuario;

import com.dentalcare.g5.main.model.dto.usuario.RolDto;
import com.dentalcare.g5.main.model.dto.usuario.RolPermisoDto;
import com.dentalcare.g5.main.model.payload.usuario.RolCreateRequest;
import com.dentalcare.g5.main.model.payload.usuario.RolFilterRequest;

import java.util.List;

/**
 * Service interface for managing roles
 */
public interface RolService {
    RolDto addRol(RolCreateRequest payload);
    RolDto updateRol(RolCreateRequest payload, Integer id);
    RolDto getRolById(int id);
    List<RolDto> getAllRoles();
    List<RolDto> filterRoles(RolFilterRequest payload);
    void deleteRol(int id);
    List<RolPermisoDto> joinRolPermisos(int id);
}

