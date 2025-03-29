package com.dentalcare.g5.main.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteDto {
    private Integer Id;
    private String Nombre;
    private String Apellido;
    private String Telefono;
    private String Email;
    private LocalDate FechaRegistro;
}
