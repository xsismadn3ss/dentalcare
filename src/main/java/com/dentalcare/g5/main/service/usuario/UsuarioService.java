package com.dentalcare.g5.main.service.usuario;

import com.dentalcare.g5.main.model.dto.usuario.UsuarioDto;
import com.dentalcare.g5.main.model.payload.usuario.UsuarioCreateRequest;
import com.dentalcare.g5.main.model.payload.usuario.UsuarioFilterRequest;

import java.util.List;

/**
 * Service interface for managing usuarios (users)
 */
public interface UsuarioService {
    UsuarioDto addUsuario(UsuarioCreateRequest payload);
    UsuarioDto updateUsuario(UsuarioDto usuarioDto);
    UsuarioDto getUsuarioById(int id);
    List<UsuarioDto> getAllUsuarios();
    List<UsuarioDto> filterUsuarios(UsuarioFilterRequest payload);
    void deleteUsuario(int id);
}

