package com.dentalcare.g5.main.service.usuario.impl;

import com.dentalcare.g5.main.mapper.PacienteMapper;
import com.dentalcare.g5.main.mapper.doctor.DoctorMapper;
import com.dentalcare.g5.main.mapper.usuario.RolMapper;
import com.dentalcare.g5.main.mapper.usuario.UsuarioMapper;
import com.dentalcare.g5.main.model.dto.PacienteDto;
import com.dentalcare.g5.main.model.dto.doctor.DoctorDto;
import com.dentalcare.g5.main.model.dto.usuario.RolDto;
import com.dentalcare.g5.main.model.dto.usuario.UsuarioDto;
import com.dentalcare.g5.main.model.entity.Paciente;
import com.dentalcare.g5.main.model.entity.doctor.Doctor;
import com.dentalcare.g5.main.model.entity.usuario.Rol;
import com.dentalcare.g5.main.model.entity.usuario.Usuario;
import com.dentalcare.g5.main.model.payload.usuario.UsuarioCreateRequest;
import com.dentalcare.g5.main.model.payload.usuario.UsuarioFilterRequest;
import com.dentalcare.g5.main.model.payload.usuario.UsuarioUpdateRequest;
import com.dentalcare.g5.main.repository.PacienteRepository;
import com.dentalcare.g5.main.repository.doctor.DoctorRepository;
import com.dentalcare.g5.main.repository.usuario.RolRepository;
import com.dentalcare.g5.main.repository.usuario.UsuarioRepository;
import com.dentalcare.g5.main.service.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private PacienteMapper pacienteMapper;

    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private RolMapper rolMapper;

    @Override
    public UsuarioDto addUser(UsuarioCreateRequest payload) {
        Usuario usuario = new Usuario();
        usuario.setNombre(payload.getNombre());
        usuario.setEmail(payload.getEmail());
        usuario.setPassword(payload.getPassword());
        Usuario savedUsuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDto(savedUsuario);
    }

    @Override
    public UsuarioDto updateUser(UsuarioUpdateRequest payload, Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setNombre(payload.getNombre());
        usuario.setEmail(payload.getEmail());
        usuario.setPassword(payload.getPassword());
        Usuario updatedUsuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDto(updatedUsuario);
    }

    @Override
    public UsuarioDto getUserById(int id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return usuarioMapper.toDto(usuario);
    }

    @Override
    public List<UsuarioDto> getAllUsers() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarioMapper.toDtoList(usuarios);
    }

    @Override
    public List<UsuarioDto> filterUsers(UsuarioFilterRequest payload, Integer id) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<Usuario> filteredUsuarios = usuarios.stream()
                .filter(usuario -> {
                    // Filtro por ID
                    if (payload.getId() != null && !payload.getId().equals(usuario.getId())) {
                        return false;
                    }
                    // Filtro por nombre
                    if (payload.getNombre() != null && !usuario.getNombre().toLowerCase().contains(payload.getNombre().toLowerCase())) {
                        return false;
                    }
                    // Filtro por email
                    return payload.getEmail() == null || 
                           usuario.getEmail().toLowerCase().contains(payload.getEmail().toLowerCase());
                })
                .toList();
        return usuarioMapper.toDtoList(filteredUsuarios);
    }

    @Override
    public void deleteUser(int id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuarioRepository.delete(usuario);
    }

    @Override
    public PacienteDto joinPaciente(int id) {
        Paciente paciente = pacienteRepository.findByUsuarioId(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        return pacienteMapper.toDto(paciente);
    }

    @Override
    public DoctorDto joinDoctor(int id) {
        Doctor doctor = doctorRepository.findByUsuarioId(id)
                .orElseThrow(() -> new RuntimeException("Doctor no encontrado"));
        return doctorMapper.toDto(doctor);
    }

    @Override
    public RolDto joinRol(int id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Rol rol = rolRepository.findById(usuario.getRol().getId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        return rolMapper.toDto(rol);
    }
}
