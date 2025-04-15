package com.dentalcare.g5.main.model.dto.usuario;

import com.dentalcare.g5.main.model.dto.PacienteDto;
import com.dentalcare.g5.main.model.dto.doctor.DoctorDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {
    private  Integer id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private RolDto rol;
    private PacienteDto paciente;
    private DoctorDto doctor;
}
