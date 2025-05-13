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
import com.dentalcare.g5.main.service.doctor.DoctorService;
import com.dentalcare.g5.main.service.paciente.PacienteService;
import com.dentalcare.g5.main.service.usuario.RolService;
import com.dentalcare.g5.main.service.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private PacienteMapper pacienteMapper;

    @Autowired
    private RolService rolService;
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
        Paciente paciente = pacienteMapper.toEntity(pacienteService.getPacienteById(id));
        return pacienteMapper.toDto(paciente);
    }

    @Override
    public DoctorDto joinDoctor(int id) {
        Doctor doctor = doctorMapper.toEntity(doctorService.getDoctorById(id));
        return doctorMapper.toDto(doctor);
    }

    @Override
    public RolDto joinRol(int usuario_id) {
        Usuario usuario = usuarioMapper.toEntity(this.getUserById(usuario_id));
        return rolMapper.toDto(usuario.getRol());
    }
}
