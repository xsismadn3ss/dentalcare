package com.dentalcare.g5.main.mapper.usuario;

import com.dentalcare.g5.main.model.dto.usuario.RolPermisoDto;
import com.dentalcare.g5.main.model.entity.usuario.RolPermiso;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RolPermisoMapper {
    RolPermisoDto toDto(RolPermiso rolPermiso);
    List<RolPermisoDto> toDtoList(List<RolPermiso> rolPermisos);
    RolPermiso toEntity(RolPermisoDto rolPermisoDto);
}
