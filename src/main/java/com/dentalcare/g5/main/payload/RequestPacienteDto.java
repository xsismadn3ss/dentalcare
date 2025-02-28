package com.dentalcare.g5.main.payload;

import lombok.Data;

@Data
public class RequestPacienteDto {
    private int id;
    private String nombre;
    private String apellido;
    private String email;
}
