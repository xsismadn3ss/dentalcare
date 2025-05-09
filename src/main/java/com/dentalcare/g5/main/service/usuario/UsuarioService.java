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
public interface UsuarioService {
    UsuarioDto addUser(UsuarioCreateRequest payload);
    UsuarioDto updateUser(UsuarioUpdateRequest payload, Integer id);
    UsuarioDto getUserById(int id);
    List<UsuarioDto> getAllUsers();
    List<UsuarioDto> filterUsers(UsuarioFilterRequest payload, Integer id);
    void deleteUser(int id);
    PacienteDto joinPaciente(int id);
    DoctorDto joinDoctor(int id);
    RolDto joinRol(int id);
}

