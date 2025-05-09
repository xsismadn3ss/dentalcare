package com.dentalcare.g5.main.model.dto.cita;


import com.dentalcare.g5.main.model.dto.PacienteDto;
import com.dentalcare.g5.main.model.dto.doctor.DoctorDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitaDto {
    private Integer id;
    private LocalDate fecha;
    private Time hora;
    private String motivo;
    private Integer doctor_id;
    private Integer paciente_id;
}

