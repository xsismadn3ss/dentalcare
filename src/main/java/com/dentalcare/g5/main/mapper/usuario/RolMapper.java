package com.dentalcare.g5.main.mapper.usuario;

import com.dentalcare.g5.main.model.dto.usuario.PermisoDto;
import com.dentalcare.g5.main.model.dto.usuario.RolDto;
import com.dentalcare.g5.main.model.dto.usuario.RolPermisoDto;
import com.dentalcare.g5.main.model.entity.usuario.Rol;
import com.dentalcare.g5.main.model.entity.usuario.RolPermiso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {UsuarioMapper.class, RolPermisoMapper.class})
public interface RolMapper {
    @Mapping(target = "rolPermisos", qualifiedByName = "mapRolPermisoWhitoutRol")
    @Mapping(target = "usuario.rol", ignore = true)
    @Mapping(target = "usuario.paciente", ignore = true)
    @Mapping(target = "usuario.doctor", ignore = true)
    RolDto toDto(Rol rol);
    Rol toEntity(RolDto rolDto);

    @Named("mapRolPermisoWhitoutRol")
    static List<RolPermisoDto> mapRolPermisoWhitoutRol(List<RolPermiso> rolPermisos){
        return rolPermisos.stream()
                .map(rolPermiso -> new RolPermisoDto(
                        rolPermiso.getId(),
                        new PermisoDto(
                                rolPermiso.getPermiso().getId(),
                                rolPermiso.getPermiso().getNombre(),
                                rolPermiso.getPermiso().getDescripcion(),
                                rolPermiso.getPermiso().getActivo(),
                                null
                        ),
                        null
                )).collect(Collectors.toList());
    }
}
