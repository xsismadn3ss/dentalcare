package com.dentalcare.g5.main.mapper.doctor;

import com.dentalcare.g5.main.model.dto.doctor.DoctorDto;
import com.dentalcare.g5.main.model.dto.doctor.EspecialidadDto;
import com.dentalcare.g5.main.model.entity.doctor.Doctor;
import com.dentalcare.g5.main.model.entity.doctor.Especialidad;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {DoctorMapper.class})
public interface EspecialidadMapper {

    @Mapping(target = "doctores", qualifiedByName = "mapDoctorsWhitoutEspecialidad")
    EspecialidadDto toDto(Especialidad especialidad);
    Especialidad toEntity(EspecialidadDto especialidadDto);

    @Named("mapDoctorsWhitoutEspecialidad")
    static List<DoctorDto> mapDoctorsWhitoutEspecialidad(List<Doctor> doctors){
        return doctors.stream()
                .map(doctor -> new DoctorDto(
                        doctor.getId(),
                        doctor.getNo_vigiliancia(),
                        null,
                        null,
                        null
                ))
                .collect(Collectors.toList());
    }
}
