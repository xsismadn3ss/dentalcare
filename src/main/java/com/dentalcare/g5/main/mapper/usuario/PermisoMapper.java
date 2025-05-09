package com.dentalcare.g5.main.mapper.usuario;

import com.dentalcare.g5.main.model.dto.usuario.PermisoDto;
import com.dentalcare.g5.main.model.entity.usuario.Permiso;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PermisoMapper {
    PermisoDto toDto(Permiso permiso);
    List<PermisoDto> toDtoList(List<Permiso> permisos);
    Permiso toEntity(PermisoDto permisoDto);
}
