package com.dentalcare.g5.main.mapper.usuario;

import com.dentalcare.g5.main.model.dto.usuario.RolDto;
import com.dentalcare.g5.main.model.entity.usuario.Rol;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RolMapper {
    RolDto toDto(Rol rol);
    Rol toEntity(RolDto rolDto);
    List<RolDto> toDtoList(List<Rol> roles);
}
