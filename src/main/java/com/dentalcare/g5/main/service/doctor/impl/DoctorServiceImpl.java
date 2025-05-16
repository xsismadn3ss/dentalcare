package com.dentalcare.g5.main.service.doctor.impl;

import com.dentalcare.g5.main.mapper.cita.CitaMapper;
import com.dentalcare.g5.main.mapper.doctor.DoctorMapper;
import com.dentalcare.g5.main.mapper.doctor.EspecialidadMapper;
import com.dentalcare.g5.main.model.dto.cita.CitaDto;
import com.dentalcare.g5.main.model.dto.doctor.DoctorDto;
import com.dentalcare.g5.main.model.dto.doctor.EspecialidadDto;
import com.dentalcare.g5.main.model.entity.cita.Cita;
import com.dentalcare.g5.main.model.entity.doctor.Doctor;
import com.dentalcare.g5.main.model.entity.doctor.Especialidad;
import com.dentalcare.g5.main.model.payload.doctor.DoctorCreateRequest;
import com.dentalcare.g5.main.model.payload.doctor.DoctorFilterRequest;
import com.dentalcare.g5.main.model.payload.doctor.DoctorUpdateRequest;
import com.dentalcare.g5.main.repository.doctor.DoctorRepository;
import com.dentalcare.g5.main.repository.cita.CitaRepository;
import com.dentalcare.g5.main.service.doctor.DoctorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private DoctorMapper doctorMapper;
    @Autowired
    private EspecialidadMapper especialidadMapper;
    @Autowired
    private CitaRepository citaRepository;
    @Autowired
    private CitaMapper citaMapper;

    @Override
    public DoctorDto addDoctor(DoctorCreateRequest payload) {
        Doctor doctor = new Doctor();
        doctor.setNo_vigiliancia(payload.getNo_vigiliancia());
        Doctor savedDoctor = doctorRepository.save(doctor);
        return doctorMapper.toDto(savedDoctor);
    }

    @Override
    public DoctorDto updateDoctor(DoctorUpdateRequest payload, int id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor no encontrado"));
        Doctor doctor_updated = doctorRepository.save(doctor);
        return doctorMapper.toDto(doctor_updated);
    }

    @Override
    public DoctorDto getDoctorById(int id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor no encontrado"));
        return doctorMapper.toDto(doctor);
    }

    @Override
    public Page<DoctorDto> getAllDoctors(Pageable pageable) {
        Page<Doctor> doctors = doctorRepository.findAll(pageable);
        return doctors.map(doctorMapper::toDto);
    }

    @Override
    public List<DoctorDto> filterDoctors(DoctorFilterRequest payload) {
        List<Doctor> doctors = doctorRepository.findAll();

        List<Doctor> filtered_doctors = doctors.stream()
                .filter(doctor -> {
                    // Filtro por ID
                    if (payload.getId() != null && !payload.getId().equals(doctor.getId())) {
                        return false;
                    }

                    // Filtro por especialidad_id
                    if (payload.getEspecialidad_id() != null &&
                            !payload.getEspecialidad_id().equals(doctor.getEspecialidad().getId())) {
                        return false;
                    }

                    // Filtro por usuario_id
                    if (payload.getUsuario_id() != null &&
                            !payload.getUsuario_id().equals(doctor.getUsuario().getId())) {
                        return false;
                    }
                    return true;
                }).toList();
        return doctorMapper.toDtoList(filtered_doctors);
    }

    @Override
    public void deleteDoctor(int id) {
        doctorRepository.deleteById(id);
    }

    @Override
    public EspecialidadDto joinEspecialidad(int doctorId) {
        Doctor doctor = doctorRepository.findById((doctorId))
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado"));
        Especialidad especialidad = doctor.getEspecialidad();
        return especialidadMapper.toDto(especialidad);
    }

    @Override
    public List<CitaDto> joinCitas(int id) {
        List<Cita> citas = citaRepository.getByDoctorId(id);
        return citaMapper.toDtoList(citas);
    }
}
