package com.dentalcare.g5.main.service.doctor;

import com.dentalcare.g5.main.model.dto.cita.CitaDto;
import com.dentalcare.g5.main.model.dto.doctor.DoctorDto;
import com.dentalcare.g5.main.model.dto.doctor.EspecialidadDto;
import com.dentalcare.g5.main.model.payload.doctor.DoctorCreateRequest;
import com.dentalcare.g5.main.model.payload.doctor.DoctorFilterRequest;
import com.dentalcare.g5.main.model.payload.doctor.DoctorUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for managing doctors
 */
public interface DoctorService {
    DoctorDto addDoctor(DoctorCreateRequest payload);
    DoctorDto updateDoctor(DoctorUpdateRequest payload, int id);
    DoctorDto getDoctorById(int id);
    Page<DoctorDto> getAllDoctors(Pageable pageable);
    List<DoctorDto> filterDoctors(DoctorFilterRequest payload);
    void deleteDoctor(int id);
    EspecialidadDto joinEspecialidad(int id);
    List<CitaDto> joinCitas(int id);
}

