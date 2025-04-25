package com.dentalcare.g5.main.mapper.cita;

import com.dentalcare.g5.main.mapper.PacienteMapper;
import com.dentalcare.g5.main.mapper.doctor.DoctorMapper;
import com.dentalcare.g5.main.model.dto.cita.CitaDto;
import com.dentalcare.g5.main.model.dto.cita.NotaDto;
import com.dentalcare.g5.main.model.dto.cita.TratamientoDto;
import com.dentalcare.g5.main.model.dto.doctor.DoctorDto;
import com.dentalcare.g5.main.model.entity.cita.Cita;
import com.dentalcare.g5.main.model.entity.cita.Nota;
import com.dentalcare.g5.main.model.entity.cita.Tratamiento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {TratamientoMapper.class, NotaMapper.class, PacienteMapper.class, DoctorMapper.class})
public interface CitaMapper {
    @Mapping(target = "doctor.citas", ignore = true)
    @Mapping(target = "paciente.citas", ignore = true)
    @Mapping(target = "tratamientos", qualifiedByName = "mapTratamientoWhitoutCitas")
    @Mapping(target = "notas", qualifiedByName = "mapNotasWithoutCitas")
    CitaDto toDto(Cita cita);
    Cita toEntity(CitaDto citaDto);

    @Named("mapTratamientoWhitoutCitas")
    static List<TratamientoDto> mapTratamientoWhitoutCitas(List<Tratamiento> tratamientos){
        return tratamientos.stream()
                .map(tratamiento -> new TratamientoDto(
                        tratamiento.getId(),
                        tratamiento.getNombre(),
                        tratamiento.getPendiente(),
                        null
                ))
                .collect(Collectors.toList());
    }

    @Named("mapNotasWithoutCitas")
    static List<NotaDto> mapNotasWithoutCitas(List<Nota> notas){
        return notas.stream()
                .map(nota -> new NotaDto(
                        nota.getId(),
                        nota.getTitulo(),
                        nota.getDescripcion(),
                        null
                ))
                .collect(Collectors.toList());
    }
}
