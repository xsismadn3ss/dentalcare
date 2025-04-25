package com.dentalcare.g5.main.mapper;

import com.dentalcare.g5.main.mapper.doctor.DoctorMapper;
import com.dentalcare.g5.main.mapper.usuario.UsuarioMapper;
import com.dentalcare.g5.main.model.dto.PacienteDto;
import com.dentalcare.g5.main.model.dto.cita.CitaDto;
import com.dentalcare.g5.main.model.entity.Paciente;
import com.dentalcare.g5.main.model.entity.cita.Cita;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {UsuarioMapper.class, DoctorMapper.class})
public interface PacienteMapper {
    @Mapping(target = "usuario.paciente", ignore = true)
    @Mapping(target = "usuario.doctor", ignore = true)
    @Mapping(target = "citas", qualifiedByName = "mapCitasSimple")
    PacienteDto toDto(Paciente paciente);

    Paciente toEntity(PacienteDto pacienteDto);

    @Named("mapCitasSimple")
    static List<CitaDto> mapCitasSimple(List<Cita> citas) {
        return citas.stream()
                .map(cita -> new CitaDto(
                        cita.getId(),
                        cita.getFecha(),
                        cita.getHora(),
                        cita.getMotivo(),
                        null,
                        null,
                        null,
                        null
                )).collect(Collectors.toList());
    }
}
