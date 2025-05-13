package com.dentalcare.g5.main.service.usuario.impl;

import com.dentalcare.g5.main.mapper.usuario.PermisoMapper;
import com.dentalcare.g5.main.mapper.usuario.RolMapper;
import com.dentalcare.g5.main.mapper.usuario.RolPermisoMapper;
import com.dentalcare.g5.main.model.dto.usuario.PermisoDto;
import com.dentalcare.g5.main.model.dto.usuario.RolDto;
import com.dentalcare.g5.main.model.dto.usuario.RolPermisoDto;
import com.dentalcare.g5.main.model.entity.usuario.Permiso;
import com.dentalcare.g5.main.model.entity.usuario.Rol;
import com.dentalcare.g5.main.model.entity.usuario.RolPermiso;
import com.dentalcare.g5.main.model.payload.usuario.RolPermisoCreateRequest;
import com.dentalcare.g5.main.model.payload.usuario.RolPermisoFilterRequest;
import com.dentalcare.g5.main.repository.usuario.RolPerRepository;
import com.dentalcare.g5.main.service.usuario.PermisoService;
import com.dentalcare.g5.main.service.usuario.RolPermisoService;
import com.dentalcare.g5.main.service.usuario.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolPermisoServiceImpl implements RolPermisoService {
    @Autowired
    private RolPerRepository rolPerRepository;
    @Autowired
    private RolPermisoMapper rolPermisoMapper;

    @Autowired
    private PermisoService permisoService;
    @Autowired
    private PermisoMapper permisoMapper;

    @Autowired
    private RolService rolService;
    @Autowired
    private RolMapper rolMapper;

    @Override
    public RolPermisoDto addRolPer(RolPermisoCreateRequest payload) {
        RolPermiso rolPermiso = new RolPermiso();

        // obtener permiso
        PermisoDto permisoDto = permisoService.getPermisoById(payload.getPermiso_id());
        Permiso permiso = permisoMapper.toEntity(permisoDto);

        // obtener rol
        RolDto rolDto = rolService.getRolById(payload.getRol_id());
        Rol rol = rolMapper.toEntity(rolDto);

        rolPermiso.setPermiso(permiso);
        rolPermiso.setRol(rol);

        RolPermiso saved_rolPermiso = rolPerRepository.save(rolPermiso);
        return  rolPermisoMapper.toDto(saved_rolPermiso);
    }

    @Override
    public RolPermisoDto updateRolPer(RolPermisoCreateRequest payload, Integer id) {
        RolPermiso rolPermiso = rolPerRepository.findById(id).orElseThrow(() -> new  RuntimeException("Relación de rol y permiso no encontrado"));

        // obtener permiso
        PermisoDto permisoDto = permisoService.getPermisoById(payload.getPermiso_id());
        Permiso permiso = permisoMapper.toEntity(permisoDto);

        // obtener rol
        RolDto rolDto = rolService.getRolById(payload.getRol_id());
        Rol rol = rolMapper.toEntity(rolDto);

        rolPermiso.setPermiso(permiso);
        rolPermiso.setRol(rol);


        RolPermiso rolPermisoUpdated = rolPerRepository.save(rolPermiso);
        return rolPermisoMapper.toDto(rolPermisoUpdated);
    }

    @Override
    public RolPermisoDto getRolPerById(int id) {
        RolPermiso rolPermiso = rolPerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relación de rol y permiso no encontrado"));
        return  rolPermisoMapper.toDto(rolPermiso);
    }

    @Override
    public List<RolPermisoDto> getAllRolPer() {
        List<RolPermiso> rolPermisos = rolPerRepository.findAll();
        return  rolPermisoMapper.toDtoList(rolPermisos);
    }

    @Override
    public List<RolPermisoDto> filterRolPer(RolPermisoFilterRequest payload) {
        List<RolPermiso> rolPermisos = rolPerRepository.findAll();
        List<RolPermiso> filteredRolPermisos = rolPermisos.stream()
                .filter(rolPermiso -> {
                    // Filtro por ID de permiso
                    if (payload.getPermiso_id() != null && !payload.getPermiso_id().equals(rolPermiso.getRol().getId())) {
                        return false;
                    }

                    // Filtro por ID de rol
                    return payload.getRol_id() == null || payload.getRol_id().equals(rolPermiso.getPermiso().getId());
                })
                .toList();
        return rolPermisoMapper.toDtoList(filteredRolPermisos);
    }

    @Override
    public void deleteRolPer(int id) {
        RolPermiso rolPermiso = rolPerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relación de rol y permiso no encontrada"));
        rolPerRepository.delete(rolPermiso);
    }
}
