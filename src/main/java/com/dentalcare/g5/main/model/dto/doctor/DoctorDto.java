package com.dentalcare.g5.main.model.dto.doctor;

import com.dentalcare.g5.main.model.dto.cita.CitaDto;
import com.dentalcare.g5.main.model.entity.usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.id.IncrementGenerator;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {
    private Integer id;
    private String no_vigilancia;
    private EspacialidadDto especialidad;
    private Usuario usuario;
    private List<CitaDto> citas;
}
