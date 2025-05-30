package com.dentalcare.g5.main.mapper.usuario;

import com.dentalcare.g5.main.model.dto.usuario.RolPermisoDto;
import com.dentalcare.g5.main.model.entity.usuario.RolPermiso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RolPermisoMapper {
    @Mapping(target = "rol_id", source = "rol.id")
    @Mapping(target = "permiso_id", source = "permiso.id")
    RolPermisoDto toDto(RolPermiso rolPermiso);

    List<RolPermisoDto> toDtoList(List<RolPermiso> rolPermisos);
    }
