package com.dentalcare.g5.main.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;

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
}

