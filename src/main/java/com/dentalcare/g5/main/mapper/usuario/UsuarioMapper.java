package com.dentalcare.g5.main.mapper.usuario;

import com.dentalcare.g5.main.model.dto.usuario.UsuarioDto;
import com.dentalcare.g5.main.model.entity.usuario.Usuario;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioDto toDto(Usuario usuario);
    Usuario toEntity(UsuarioDto usuarioDto);
    List<UsuarioDto> toDtoList(List<Usuario> usuarios);
}
