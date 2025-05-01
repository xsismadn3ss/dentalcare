package com.dentalcare.g5.main.model.dto;

import com.dentalcare.g5.main.model.dto.cita.CitaDto;
import com.dentalcare.g5.main.model.dto.usuario.UsuarioDto;
import com.dentalcare.g5.main.model.entity.usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteDto {
    private Integer id;
    private LocalDate fechaRegistro;
    private UsuarioDto usuario;
    // private List<CitaDto> citas;    (REDUNDANCIA)
}
