package com.dentalcare.g5.main.mapper.cita;

import com.dentalcare.g5.main.model.dto.cita.TratamientoDto;
import com.dentalcare.g5.main.model.entity.cita.Tratamiento;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TratamientoMapper {
    TratamientoDto toDto(Tratamiento tratamiento);
    Tratamiento toEntity(TratamientoDto tratamientoDto);
}
