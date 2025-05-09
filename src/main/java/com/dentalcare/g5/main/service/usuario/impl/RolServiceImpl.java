package com.dentalcare.g5.main.service.usuario.impl;

import com.dentalcare.g5.main.mapper.usuario.RolMapper;
import com.dentalcare.g5.main.mapper.usuario.RolPermisoMapper;
import com.dentalcare.g5.main.model.dto.usuario.RolDto;
import com.dentalcare.g5.main.model.dto.usuario.RolPermisoDto;
import com.dentalcare.g5.main.model.entity.usuario.Rol;
import com.dentalcare.g5.main.model.entity.usuario.RolPermiso;
import com.dentalcare.g5.main.model.payload.usuario.RolCreateRequest;
import com.dentalcare.g5.main.model.payload.usuario.RolFilterRequest;
import com.dentalcare.g5.main.repository.usuario.RolPerRepository;
import com.dentalcare.g5.main.repository.usuario.RolRepository;
import com.dentalcare.g5.main.service.usuario.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolServiceImpl implements RolService {
    @Autowired
    private RolMapper rolMapper;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private RolPerRepository rolPerRepository;
    @Autowired
    private RolPermisoMapper rolPermisoMapper;

    @Override
    public RolDto addRol(RolCreateRequest payload) {
        Rol rol = new Rol();
        rol.setNombre(payload.getNombre());
        Rol savedRol = rolRepository.save(rol);
        return rolMapper.toDto(savedRol);
    }

    @Override
    public RolDto updateRol(RolCreateRequest payload, Integer id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        rol.setNombre(payload.getNombre());
        Rol updatedRol = rolRepository.save(rol);
        return rolMapper.toDto(updatedRol);
    }

    @Override
    public RolDto getRolById(int id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        return rolMapper.toDto(rol);
    }

    @Override
    public List<RolDto> getAllRoles() {
        List<Rol> roles = rolRepository.findAll();
        return rolMapper.toDtoList(roles);
    }

    @Override
    public List<RolDto> filterRoles(RolFilterRequest payload) {
        List<Rol> roles = rolRepository.findAll();
        List<Rol> filteredRoles = roles.stream()
                .filter(rol -> {
                    // Filtro por ID
                    if (payload.getId() != null && !payload.getId().equals(rol.getId())) {
                        return false;
                    }
                    // Filtro por nombre
                    return payload.getNombre() == null || 
                           rol.getNombre().toLowerCase().contains(payload.getNombre().toLowerCase());
                })
                .toList();
        return rolMapper.toDtoList(filteredRoles);
    }

    @Override
    public void deleteRol(int id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        rolRepository.delete(rol);
    }

    @Override
    public List<RolPermisoDto> joinRolPermisos(int id) {
        List<RolPermiso> rolPermisos = rolPerRepository.findByRolId(id);
        return rolPermisoMapper.toDtoList(rolPermisos);
    }
}
