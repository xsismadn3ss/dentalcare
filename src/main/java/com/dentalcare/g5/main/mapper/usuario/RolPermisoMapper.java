package com.dentalcare.g5.main.mapper.usuario;

import com.dentalcare.g5.main.model.dto.usuario.RolPermisoDto;
import com.dentalcare.g5.main.model.entity.usuario.RolPermiso;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RolPermisoMapper {
    RolPermisoDto toDto(RolPermiso rolPermiso);
    RolPermiso toEntity(RolPermisoDto rolPermisoDto);
}
