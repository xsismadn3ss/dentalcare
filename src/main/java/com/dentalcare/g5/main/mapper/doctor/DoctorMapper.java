package com.dentalcare.g5.main.mapper.doctor;

import com.dentalcare.g5.main.mapper.cita.CitaMapper;
import com.dentalcare.g5.main.mapper.usuario.UsuarioMapper;
import com.dentalcare.g5.main.model.dto.cita.CitaDto;
import com.dentalcare.g5.main.model.dto.cita.NotaDto;
import com.dentalcare.g5.main.model.dto.doctor.DoctorDto;
import com.dentalcare.g5.main.model.dto.usuario.UsuarioDto;
import com.dentalcare.g5.main.model.entity.cita.Cita;
import com.dentalcare.g5.main.model.entity.cita.Nota;
import com.dentalcare.g5.main.model.entity.doctor.Doctor;
import com.dentalcare.g5.main.model.entity.usuario.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {EspecialidadMapper.class, UsuarioMapper.class})
public interface DoctorMapper {
    @Mapping(target = "usuario.doctor", ignore = true)
    @Mapping(target = "especialidad.doctores", ignore = true)
    @Mapping(target = "citas", qualifiedByName = "mapCitasSimple")
    DoctorDto toDto(Doctor doctor);

    Doctor toEntity(DoctorDto doctorDto);

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
