package com.dentalcare.g5.main.service.usuario.impl;

import com.dentalcare.g5.main.mapper.usuario.PermisoMapper;
import com.dentalcare.g5.main.mapper.usuario.RolPermisoMapper;
import com.dentalcare.g5.main.model.dto.usuario.PermisoDto;
import com.dentalcare.g5.main.model.dto.usuario.RolPermisoDto;
import com.dentalcare.g5.main.model.entity.usuario.Permiso;
import com.dentalcare.g5.main.model.entity.usuario.RolPermiso;
import com.dentalcare.g5.main.model.payload.usuario.PermisoCreateRequest;
import com.dentalcare.g5.main.model.payload.usuario.PermisoFilterRequest;
import com.dentalcare.g5.main.model.payload.usuario.PermisoUpdateRequest;
import com.dentalcare.g5.main.repository.usuario.PermisoRepository;
import com.dentalcare.g5.main.repository.usuario.RolPerRepository;
import com.dentalcare.g5.main.service.usuario.PermisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public class PermisoServiceImpl implements PermisoService {

    @Autowired
    private PermisoRepository permisoRepository;
    @Autowired
    private RolPerRepository rolPerRepository;
    @Autowired
    private PermisoMapper permisoMapper;
    @Autowired
    private RolPermisoMapper rolPermisoMapper;

    @Override
    public PermisoDto addPermiso(PermisoCreateRequest payload) {
        Permiso permiso = new Permiso(null, payload.getNombre(), payload.getDescripcion(), true, null);
        Permiso permiso_guardado = permisoRepository.save(permiso);
        return  permisoMapper.toDto(permiso_guardado);
    }

    @Override
    public PermisoDto updatePermiso(PermisoUpdateRequest payload, Integer id) {
        Permiso permiso = permisoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Permiso no encontrado"));
        
        permiso.setNombre(payload.getNombre());
        permiso.setDescripcion(payload.getDescripcion());
        permiso.setActivo(payload.getActivo());
        
        Permiso permisoActualizado = permisoRepository.save(permiso);
        return permisoMapper.toDto(permisoActualizado);
    }

    @Override
    public PermisoDto getPermisoById(int id) {
        Permiso permiso = permisoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Permiso no encontrado"));
        return permisoMapper.toDto(permiso);
    }

    @Override
    public List<PermisoDto> getAllPermisos() {
        List<Permiso> permisos = permisoRepository.findAll();
        return permisoMapper.toDtoList(permisos);
    }

    @Override
    public List<PermisoDto> filterPermisos(PermisoFilterRequest payload) {
        List<Permiso> permisos = permisoRepository.findAll();
        List<Permiso> filteredPermisos = permisos.stream()
                .filter(permiso -> {
                    // ID filter
                    if (payload.getId() != null && !payload.getId().equals(permiso.getId())) {
                        return false;
                    }

                    // Nombre filter
                    if (payload.getNombre() != null && (permiso.getNombre() == null ||
                            !permiso.getNombre().toLowerCase().contains(payload.getNombre().toLowerCase()))) {
                        return false;
                    }

                    // Descripcion filter
                    if (payload.getDescripcion() != null && (permiso.getDescripcion() == null ||
                            !permiso.getDescripcion().toLowerCase().contains(payload.getDescripcion().toLowerCase()))) {
                        return false;
                    }

                    // Activo filter
                    return payload.getActivo() == null ||
                            (permiso.getActivo() != null && payload.getActivo().equals(permiso.getActivo()));
                })
                .toList();
        return permisoMapper.toDtoList(filteredPermisos);
    }

    @Override
    public void deletePermiso(int id) {
        Permiso permiso = permisoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permiso not found with ID: " + id));

        try {
            // Delete any associated RolPermiso records first
            if (permiso.getRolPermisos() != null && !permiso.getRolPermisos().isEmpty()) {
                permiso.getRolPermisos().clear();
                permisoRepository.save(permiso);
            }

            // Then delete the permiso
            permisoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Cannot delete permiso due to existing relationships", e);
        }
    }

    @Override
    public List<RolPermisoDto> joinRolPermiso(int id) {
        List<RolPermiso> rolPermisos = rolPerRepository.findByPermisoId(id);
        return rolPermisoMapper.toDtoList(rolPermisos);
    }
}
