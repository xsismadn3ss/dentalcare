package com.dentalcare.g5.main.mapper.usuario;

import com.dentalcare.g5.main.mapper.doctor.DoctorMapper;
import com.dentalcare.g5.main.model.dto.usuario.RolPermisoDto;
import com.dentalcare.g5.main.model.entity.usuario.RolPermiso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RolMapper.class, PermisoMapper.class, DoctorMapper.class})
public interface RolPermisoMapper {
    @Mapping(target = "permiso.rolPermisos", ignore = true)
    @Mapping(target = "rol.rolPermisos", ignore = true)
    @Mapping(target = "rol.usuario.paciente.citas[].doctor.especialidad.doctores", ignore = true)
    RolPermisoDto toDto(RolPermiso rolPermiso);
    
    RolPermiso toEntity(RolPermisoDto rolPermisoDto);
}
