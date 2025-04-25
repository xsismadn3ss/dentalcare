package com.dentalcare.g5.main.mapper.cita;

import com.dentalcare.g5.main.model.dto.cita.CitaDto;
import com.dentalcare.g5.main.model.dto.cita.TratamientoDto;
import com.dentalcare.g5.main.model.entity.cita.Tratamiento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CitaMapper.class})
public interface TratamientoMapper {
    @Mapping(target = "cita.tratamientos", ignore = true)
    TratamientoDto toDto(Tratamiento tratamiento);
    Tratamiento toEntity(TratamientoDto tratamientoDto);
}
