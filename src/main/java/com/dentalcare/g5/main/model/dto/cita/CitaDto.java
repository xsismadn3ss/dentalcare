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
    private Integer Id;
    private Integer PacienteID;
    private Integer DoctorID;
    private LocalDate Fecha;
    private Time Hora;
    private String Estado;
    private String Motivo;
    private DoctorDto doctor;
    private PacienteDto paciente;
    private List<NotaDto> notas;
    private List<Tratamiento> tratamientos;
}

