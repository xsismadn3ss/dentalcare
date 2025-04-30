package com.dentalcare.g5.main.mapper.usuario;

import com.dentalcare.g5.main.model.dto.usuario.PermisoDto;
import com.dentalcare.g5.main.model.dto.usuario.RolDto;
import com.dentalcare.g5.main.model.dto.usuario.RolPermisoDto;
import com.dentalcare.g5.main.model.entity.usuario.Permiso;
import com.dentalcare.g5.main.model.entity.usuario.RolPermiso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {RolPermisoMapper.class})
public interface PermisoMapper {
    @Mapping(target = "rolPermisos", qualifiedByName = "mapRolPermisoWithoutPermiso")
    PermisoDto toDto(Permiso permiso);
    Permiso toEntity(PermisoDto permisoDto);

    @Named("mapRolPermisoWithoutPermiso")
    static List<RolPermisoDto> mapRolPermisoWithoutPermiso(List<RolPermiso> rolPermisos){
        return rolPermisos.stream()
                .map(rolPermiso -> new RolPermisoDto(
                        rolPermiso.getId(),
                        null,
                        new RolDto(
                                rolPermiso.getRol().getId(),
                                rolPermiso.getRol().getNombre(),
                                null,
                                null
                        )
                )).collect(Collectors.toList());
    }
}
