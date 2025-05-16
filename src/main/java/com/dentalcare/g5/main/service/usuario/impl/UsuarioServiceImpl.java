package com.dentalcare.g5.main.service.usuario.impl;

import com.dentalcare.g5.main.mapper.PacienteMapper;
import com.dentalcare.g5.main.mapper.doctor.DoctorMapper;
import com.dentalcare.g5.main.mapper.usuario.RolMapper;
import com.dentalcare.g5.main.mapper.usuario.UsuarioMapper;
import com.dentalcare.g5.main.model.dto.PacienteDto;
import com.dentalcare.g5.main.model.dto.doctor.DoctorDto;
import com.dentalcare.g5.main.model.dto.usuario.RolDto;
import com.dentalcare.g5.main.model.dto.usuario.UsuarioDto;
import com.dentalcare.g5.main.model.entity.usuario.Usuario;
import com.dentalcare.g5.main.model.payload.usuario.UsuarioCreateRequest;
import com.dentalcare.g5.main.model.payload.usuario.UsuarioFilterRequest;
import com.dentalcare.g5.main.model.payload.usuario.UsuarioUpdateRequest;
import com.dentalcare.g5.main.repository.usuario.UsuarioRepository;
import com.dentalcare.g5.main.service.usuario.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private DoctorMapper doctorMapper;
    @Autowired
    private PacienteMapper pacienteMapper;
    @Autowired
    private RolMapper rolMapper;

    @Override
    public UsuarioDto addUser(UsuarioCreateRequest payload) {
        Usuario usuario = new Usuario();
        usuario.setNombre(payload.getNombre());
        usuario.setApellido(payload.getApellido());
        usuario.setTelefono(payload.getTelefono());
        usuario.setUsername(payload.getUsername());
        usuario.setEmail(payload.getEmail());
        usuario.setPassword(payload.getPassword());
        Usuario savedUsuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDto(savedUsuario);
    }

    @Override
    public UsuarioDto updateUser(UsuarioUpdateRequest payload, Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        usuario.setNombre(payload.getNombre());
        usuario.setApellido(payload.getApellido());
        usuario.setTelefono(payload.getTelefono());
        usuario.setUsername(payload.getUsername());
        usuario.setEmail(payload.getEmail());
        usuario.setPassword(payload.getPassword());
        Usuario updatedUsuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDto(updatedUsuario);
    }

    @Override
    public UsuarioDto getUserById(int id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        return usuarioMapper.toDto(usuario);
    }

    @Override
    public Page<UsuarioDto> getAllUsers(Pageable pageable) {
        Page<Usuario> usuariosPage = usuarioRepository.findAll(pageable);
        return usuariosPage.map(usuarioMapper::toDto);
    }

    @Override
    public List<UsuarioDto> filterUsers(UsuarioFilterRequest payload) {
        List<Usuario> usuarios = usuarioRepository.filterUsuarios(
                payload.getNombre(),
                payload.getApellido(),
                payload.getEmail(),
                payload.getTelefono(),
                payload.getRol_id()
        );
        return usuarioMapper.toDtoList(usuarios);
    }

    @Override
    public void deleteUser(int id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        usuarioRepository.delete(usuario);
    }

    @Override
    public PacienteDto joinPaciente(int id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        return pacienteMapper.toDto(usuario.getPaciente());
    }

    @Override
    public DoctorDto joinDoctor(int id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        return doctorMapper.toDto(usuario.getDoctor());
    }

    @Override
    public RolDto joinRol(int usuario_id) {
        Usuario usuario = usuarioRepository.findById(usuario_id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        return rolMapper.toDto(usuario.getRol());
    }
}
