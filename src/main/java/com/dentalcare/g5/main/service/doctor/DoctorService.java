package com.dentalcare.g5.main.service.doctor;

import com.dentalcare.g5.main.model.dto.cita.CitaDto;
import com.dentalcare.g5.main.model.dto.doctor.DoctorDto;
import com.dentalcare.g5.main.model.dto.doctor.EspecialidadDto;
import com.dentalcare.g5.main.model.payload.doctor.DoctorCreateRequest;
import com.dentalcare.g5.main.model.payload.doctor.DoctorFilterRequest;
import com.dentalcare.g5.main.model.payload.doctor.DoctorUpdateRequest;

import java.util.List;

/**
 * Service interface for managing doctors
 */
public interface DoctorService {
    DoctorDto addDoctor(DoctorCreateRequest payload);
    DoctorDto updateDoctor(DoctorUpdateRequest payload);
    DoctorDto getDoctorById(int id);
    List<DoctorDto> getAllDoctors();
    List<DoctorDto> filterDoctors(DoctorFilterRequest payload);
    void deleteDoctor(int id);
    EspecialidadDto joinEspecialidad(int id);
    List<CitaDto> joinCitas(int id);
}

