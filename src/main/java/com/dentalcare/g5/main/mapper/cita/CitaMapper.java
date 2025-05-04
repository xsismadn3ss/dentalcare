package com.dentalcare.g5.main.mapper.cita;

import com.dentalcare.g5.main.model.dto.cita.CitaDto;
import com.dentalcare.g5.main.model.entity.cita.Cita;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CitaMapper {
    CitaDto toDto(Cita cita);
    Cita toEntity(CitaDto citaDto);
}
