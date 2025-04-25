package com.dentalcare.g5.main.service.doctor.impl;

import com.dentalcare.g5.main.mapper.doctor.DoctorMapper;
import com.dentalcare.g5.main.model.dto.doctor.DoctorDto;
import com.dentalcare.g5.main.model.entity.doctor.Doctor;
import com.dentalcare.g5.main.model.entity.doctor.Especialidad;
import com.dentalcare.g5.main.model.entity.usuario.Usuario;
import com.dentalcare.g5.main.model.payload.doctor.DoctorCreateRequest;
import com.dentalcare.g5.main.model.payload.doctor.DoctorFilterRequest;
import com.dentalcare.g5.main.model.payload.doctor.DoctorUpdateRequest;
import com.dentalcare.g5.main.repository.doctor.DoctorRepository;
import com.dentalcare.g5.main.repository.doctor.EspecialidadRepository;
import com.dentalcare.g5.main.repository.usuario.UsuarioRepository;
import com.dentalcare.g5.main.service.doctor.DoctorService;
import com.dentalcare.g5.main.service.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the DoctorService interface
 */
@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final EspecialidadRepository especialidadRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;
    private final DoctorMapper doctorMapper;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository,
                            EspecialidadRepository especialidadRepository,
                            UsuarioRepository usuarioRepository,
                            UsuarioService usuarioService,
                            DoctorMapper doctorMapper) {
        this.doctorRepository = doctorRepository;
        this.especialidadRepository = especialidadRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
        this.doctorMapper = doctorMapper;
    }

    /**
     * Creates a new doctor
     * @param payload The doctor creation request
     * @return The created doctor DTO
     */
    @Override
    @Transactional
    public DoctorDto addDoctor(DoctorCreateRequest payload) {
        // First, create the usuario from the request
        Usuario usuario = new Usuario();
        usuario.setNombre(payload.getNombre());
        usuario.setApellido(payload.getApellido());
        usuario.setEmail(payload.getUsuario().getEmail());
        usuario.setUsername(payload.getUsuario().getUsername());
        usuario.setPassword(payload.getUsuario().getPassword()); // In production, this would be encrypted
        usuario.setTelefono(payload.getTelefono());
        
        Usuario savedUsuario = usuarioRepository.save(usuario);
        
        // Find the especialidad by ID
        Especialidad especialidad = especialidadRepository.findById(payload.getEspecialidadId())
                .orElseThrow(() -> new RuntimeException("Especialidad not found with ID: " + payload.getEspecialidadId()));
        
        // Create new doctor entity
        Doctor doctor = Doctor.builder()
                .no_vigiliancia(payload.getNoVigilancia())
                .especialidad(especialidad)
                .usuario(savedUsuario)
                .citas(new ArrayList<>())
                .build();
        
        // Save the entity and return mapped DTO
        Doctor savedDoctor = doctorRepository.save(doctor);
        return doctorMapper.toDto(savedDoctor);
    }

    /**
     * Updates an existing doctor
     * @param payload The doctor update request
     * @return The updated doctor DTO
     */
    @Override
    @Transactional
    public DoctorDto updateDoctor(DoctorUpdateRequest payload) {
        // Find the doctor by ID
        Doctor existingDoctor = doctorRepository.findById(payload.getId())
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + payload.getId()));
        
        // Update fields
        if (payload.getNombre() != null || payload.getApellido() != null || payload.getTelefono() != null) {
            Usuario usuario = existingDoctor.getUsuario();
            
            if (payload.getNombre() != null) {
                usuario.setNombre(payload.getNombre());
            }
            
            if (payload.getApellido() != null) {
                usuario.setApellido(payload.getApellido());
            }
            
            if (payload.getTelefono() != null) {
                usuario.setTelefono(payload.getTelefono());
            }
            
            usuarioRepository.save(usuario);
        }
        
        if (payload.getNoVigilancia() != null) {
            existingDoctor.setNo_vigiliancia(payload.getNoVigilancia());
        }
        
        // Save updated entity and return mapped DTO
        Doctor updatedDoctor = doctorRepository.save(existingDoctor);
        return doctorMapper.toDto(updatedDoctor);
    }

    /**
     * Retrieves a doctor by ID
     * @param id The doctor ID
     * @return The doctor DTO
     */
    @Override
    public DoctorDto getDoctorById(int id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + id));
        return doctorMapper.toDto(doctor);
    }

    /**
     * Retrieves all doctors
     * @return List of all doctor DTOs
     */
    @Override
    public List<DoctorDto> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctors.stream()
                .map(doctorMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Filters doctors based on criteria in the payload
     * @param payload Filter criteria
     * @return List of filtered doctor DTOs
     */
    @Override
    public List<DoctorDto> filterDoctors(DoctorFilterRequest payload) {
        // In a real implementation, this would use JPA Specification or Criteria API
        // For simplicity, we'll fetch all and filter in memory
        List<Doctor> allDoctors = doctorRepository.findAll();
        
        List<Doctor> filteredDoctors = allDoctors.stream()
                .filter(doctor -> {
                    // ID filter
                    if (payload.getId() != null && !payload.getId().equals(doctor.getId())) {
                        return false;
                    }
                    
                    // No vigilancia filter
                    if (payload.getNoVigilancia() != null && 
                            (doctor.getNo_vigiliancia() == null || 
                             !doctor.getNo_vigiliancia().toLowerCase().contains(payload.getNoVigilancia().toLowerCase()))) {
                        return false;
                    }
                    
                    // Especialidad ID filter
                    if (payload.getEspecialidadId() != null && 
                            (doctor.getEspecialidad() == null || 
                             !payload.getEspecialidadId().equals(doctor.getEspecialidad().getId()))) {
                        return false;
                    }
                    
                    // Usuario ID filter
                    if (payload.getUsuarioId() != null && 
                            (doctor.getUsuario() == null || 
                             !payload.getUsuarioId().equals(doctor.getUsuario().getId()))) {
                        return false;
                    }
                    
                    // Usuario attributes filters
                    if (doctor.getUsuario() != null) {
                        // Nombre filter
                        if (payload.getNombre() != null && 
                                (doctor.getUsuario().getNombre() == null || 
                                 !doctor.getUsuario().getNombre().toLowerCase().contains(payload.getNombre().toLowerCase()))) {
                            return false;
                        }
                        
                        // Apellido filter
                        if (payload.getApellido() != null && 
                                (doctor.getUsuario().getApellido() == null || 
                                 !doctor.getUsuario().getApellido().toLowerCase().contains(payload.getApellido().toLowerCase()))) {
                            return false;
                        }
                        
                        // Email filter
                        if (payload.getEmail() != null && 
                                (doctor.getUsuario().getEmail() == null || 
                                 !doctor.getUsuario().getEmail().toLowerCase().contains(payload.getEmail().toLowerCase()))) {
                            return false;
                        }
                        
                        // Teléfono filter
                        if (payload.getTelefono() != null && 
                                (doctor.getUsuario().getTelefono() == null || 
                                 !doctor.getUsuario().getTelefono().toLowerCase().contains(payload.getTelefono().toLowerCase()))) {
                            return false;
                        }
                    } else if (payload.getNombre() != null || payload.getApellido() != null || 
                               payload.getEmail() != null || payload.getTelefono() != null) {
                        return false;
                    }
                    
                    return true;
                })
                .collect(Collectors.toList());
        
        return filteredDoctors.stream()
                .map(doctorMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Deletes a doctor by ID
     * @param id The doctor ID to delete
     */
    @Override
    @Transactional
    public void deleteDoctor(int id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + id));
        
        // Delete the doctor
        doctorRepository.deleteById(id);
        
        // Optionally, delete the associated user if needed
        // usuarioRepository.deleteById(doctor.getUsuario().getId());
    }
}

