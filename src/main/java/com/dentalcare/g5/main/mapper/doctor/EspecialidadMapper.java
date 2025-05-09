package com.dentalcare.g5.main.mapper.doctor;

import com.dentalcare.g5.main.model.dto.doctor.EspecialidadDto;
import com.dentalcare.g5.main.model.entity.doctor.Especialidad;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EspecialidadMapper {
    EspecialidadDto toDto(Especialidad especialidad);
    List<EspecialidadDto> toDto(List<Especialidad> especialidades);
    Especialidad toEntity(EspecialidadDto especialidadDto);
}
