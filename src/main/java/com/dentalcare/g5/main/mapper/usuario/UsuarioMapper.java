package com.dentalcare.g5.main.mapper.usuario;

import com.dentalcare.g5.main.mapper.PacienteMapper;
import com.dentalcare.g5.main.mapper.doctor.DoctorMapper;
import com.dentalcare.g5.main.model.dto.usuario.RolDto;
import com.dentalcare.g5.main.model.dto.usuario.UsuarioDto;
import com.dentalcare.g5.main.model.entity.usuario.Rol;
import com.dentalcare.g5.main.model.entity.usuario.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {PacienteMapper.class, DoctorMapper.class, RolMapper.class})
public interface UsuarioMapper {
    @Mapping(target = "paciente.usuario", ignore = true)
    @Mapping(target = "doctor.usuario", ignore = true)
    @Mapping(target = "doctor.especialidad.doctores", ignore = true)
    UsuarioDto toDto(Usuario usuario);
    Usuario toEntity(UsuarioDto usuarioDto);

    @Named("mapRolWithoutUsuario")
    static RolDto mapRolWithoutUsuario(Rol rol){
        return new RolDto(
                rol.getId(),
                rol.getNombre(),
                null,
                null
        );
    }
}
