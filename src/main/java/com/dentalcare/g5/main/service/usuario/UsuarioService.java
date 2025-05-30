package com.dentalcare.g5.main.service.usuario;

import com.dentalcare.g5.main.model.dto.PacienteDto;
import com.dentalcare.g5.main.model.dto.doctor.DoctorDto;
import com.dentalcare.g5.main.model.dto.usuario.RolDto;
import com.dentalcare.g5.main.model.dto.usuario.UsuarioDto;
import com.dentalcare.g5.main.model.payload.usuario.UsuarioCreateRequest;
import com.dentalcare.g5.main.model.payload.usuario.UsuarioFilterRequest;
import com.dentalcare.g5.main.model.payload.usuario.UsuarioUpdateRequest;

import java.util.List;

/**
 * Service interface for managing usuarios (users)
 */
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioService {
    UsuarioDto addUser(UsuarioCreateRequest payload);
    UsuarioDto updateUser(UsuarioUpdateRequest payload, Integer id);
    UsuarioDto getUserById(int id);
    Page<UsuarioDto> getAllUsers(Pageable pageable);
    List<UsuarioDto> filterUsers(UsuarioFilterRequest payload);
    void deleteUser(int id);
    PacienteDto joinPaciente(int id);
    DoctorDto joinDoctor(int id);
    RolDto joinRol(int usuario_id);
    Boolean authenticate(String username, String password);
    UsuarioDto findByUsername(String username); // Nuevo método
}

