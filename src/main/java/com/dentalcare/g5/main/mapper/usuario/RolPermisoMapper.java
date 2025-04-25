package com.dentalcare.g5.main.mapper.usuario;

import com.dentalcare.g5.main.model.dto.usuario.RolPermisoDto;
import com.dentalcare.g5.main.model.entity.usuario.RolPermiso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RolMapper.class, PermisoMapper.class})
public interface RolPermisoMapper {
    @Mapping(target = "permiso.rolpermisos", ignore = true)
    @Mapping(target = "rol.rolpermisos", ignore = true)
    RolPermisoDto toDto(RolPermiso rolPermiso);
    RolPermiso toEntity(RolPermisoDto rolPermisoDto);
}
