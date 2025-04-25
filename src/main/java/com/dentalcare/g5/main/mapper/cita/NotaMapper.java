package com.dentalcare.g5.main.mapper.cita;

import com.dentalcare.g5.main.model.dto.cita.NotaDto;
import com.dentalcare.g5.main.model.entity.cita.Nota;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CitaMapper.class})
public interface NotaMapper {
    @Mapping(target = "cita.notas", ignore = true)
    NotaDto toDto(Nota nota);
    Nota nota(NotaDto notaDto);
}
