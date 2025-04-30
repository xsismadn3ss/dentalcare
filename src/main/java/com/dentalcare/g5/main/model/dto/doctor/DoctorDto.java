package com.dentalcare.g5.main.model.dto.doctor;

import com.dentalcare.g5.main.model.dto.cita.CitaDto;
import com.dentalcare.g5.main.model.dto.usuario.UsuarioDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {
    private Integer id;
    private EspecialidadDto especialidad;
    private UsuarioDto usuario;
    private List<CitaDto> citas;
}
