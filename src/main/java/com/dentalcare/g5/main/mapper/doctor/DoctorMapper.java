package com.dentalcare.g5.main.mapper.doctor;

import com.dentalcare.g5.main.model.dto.doctor.DoctorDto;
import com.dentalcare.g5.main.model.entity.doctor.Doctor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    DoctorDto toDto(Doctor doctor);
    List<DoctorDto> toDtoList(List<Doctor> doctors);
    Doctor toEntity(DoctorDto doctorDto);
}
