package com.dentalcare.g5.main.mapper.usuario;

import com.dentalcare.g5.main.model.dto.usuario.PermisoDto;
import com.dentalcare.g5.main.model.entity.usuario.Permiso;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermisoMapper {
    PermisoDto toDto(Permiso permiso);
    Permiso toEntity(PermisoDto permisoDto);
}
