package com.dentalcare.g5.main.payload;

import lombok.Data;

@Data
public class RequestCitaDto {
    private Integer id;
    private Integer pacienteId;
    private Integer doctorId;
    private String motivo;
}
