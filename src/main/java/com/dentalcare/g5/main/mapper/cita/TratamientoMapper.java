package com.dentalcare.g5.main.mapper.cita;

import com.dentalcare.g5.main.mapper.doctor.DoctorMapper;
import com.dentalcare.g5.main.model.dto.cita.CitaDto;
import com.dentalcare.g5.main.model.dto.cita.TratamientoDto;
import com.dentalcare.g5.main.model.entity.cita.Tratamiento;
import com.dentalcare.g5.main.model.entity.doctor.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CitaMapper.class, DoctorMapper.class})
public interface TratamientoMapper {
    @Mapping(target = "cita.tratamientos", ignore = true)
    @Mapping(target = "cita.doctor.especialidad.doctores", ignore = true)
    @Mapping(target = "cita.paciente.usuario.doctor.especialidad.doctores", ignore = true)
    TratamientoDto toDto(Tratamiento tratamiento);
    Tratamiento toEntity(TratamientoDto tratamientoDto);
}
