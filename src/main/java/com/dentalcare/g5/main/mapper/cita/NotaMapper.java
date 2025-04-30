package com.dentalcare.g5.main.mapper.cita;

import com.dentalcare.g5.main.mapper.doctor.DoctorMapper;
import com.dentalcare.g5.main.model.dto.cita.NotaDto;
import com.dentalcare.g5.main.model.entity.cita.Nota;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CitaMapper.class, DoctorMapper.class})
public interface NotaMapper {
    @Mapping(target = "cita.notas", ignore = true)
    @Mapping(target = "cita.doctor.especialidad.doctores", ignore = true)
    @Mapping(target = "cita.paciente.usuario.doctor", ignore = true)
    NotaDto toDto(Nota nota);
    Nota nota(NotaDto notaDto);
}
