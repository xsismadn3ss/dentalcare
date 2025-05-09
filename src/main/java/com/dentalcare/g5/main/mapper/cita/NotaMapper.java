package com.dentalcare.g5.main.mapper.cita;

import com.dentalcare.g5.main.model.dto.cita.NotaDto;
import com.dentalcare.g5.main.model.entity.cita.Nota;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotaMapper {
    NotaDto toDto(Nota nota);
    List<NotaDto> toDtoList(List<Nota> notas);
    Nota nota(NotaDto notaDto);
}
