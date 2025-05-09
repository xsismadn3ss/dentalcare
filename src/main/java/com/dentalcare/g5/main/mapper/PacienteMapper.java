package com.dentalcare.g5.main.mapper;

import com.dentalcare.g5.main.model.dto.PacienteDto;
import com.dentalcare.g5.main.model.entity.Paciente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PacienteMapper {
    PacienteDto toDto(Paciente paciente);
    List<PacienteDto> toDto(List<Paciente> pacientes);
    Paciente toEntity(PacienteDto pacienteDto);
}
