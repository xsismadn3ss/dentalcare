package com.dentalcare.g5.main.mapper.doctor;

import com.dentalcare.g5.main.model.dto.doctor.DoctorDto;
import com.dentalcare.g5.main.model.dto.doctor.EspecialidadDto;
import com.dentalcare.g5.main.model.entity.doctor.Doctor;
import com.dentalcare.g5.main.model.entity.doctor.Especialidad;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DoctorMapper.class})
public interface EspecialidadMapper {

    @Mapping(target = "doctores", ignore = true)
    EspecialidadDto toDto(Especialidad especialidad);

    @Mapping(target = "doctores", ignore = true)
    Especialidad toEntity(EspecialidadDto especialidadDto);

    @Named("mapDoctorsSimple")
    static List<DoctorDto> mapDoctorsSimple(List<Doctor> doctors){
        return doctors.stream()
                .map(doctor -> new DoctorDto(
                        doctor.getId(),
                        null,
                        null,
                        null
                )).toList();
    }
}
