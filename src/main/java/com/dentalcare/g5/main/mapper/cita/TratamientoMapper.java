package com.dentalcare.g5.main.mapper.cita;

import com.dentalcare.g5.main.model.dto.cita.TratamientoDto;
import com.dentalcare.g5.main.model.entity.cita.Tratamiento;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TratamientoMapper {
    TratamientoDto toDto(Tratamiento tratamiento);
    List<TratamientoDto> toDtoList(List<Tratamiento> tratamientos);
    Tratamiento toEntity(TratamientoDto tratamientoDto);
}
