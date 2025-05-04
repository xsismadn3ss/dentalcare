package com.dentalcare.g5.main.mapper;

import com.dentalcare.g5.main.model.dto.PacienteDto;
import com.dentalcare.g5.main.model.entity.Paciente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PacienteMapper {
    @Mapping(target = "usuario.paciente", ignore = true)
    @Mapping(target = "usuario.doctor", ignore = true)
    PacienteDto toDto(Paciente paciente);
    Paciente toEntity(PacienteDto pacienteDto);
}
