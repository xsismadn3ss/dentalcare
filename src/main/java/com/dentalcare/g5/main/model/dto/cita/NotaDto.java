package com.dentalcare.g5.main.model.dto.cita;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotaDto {
    private Integer id;
    private  String titulo;
    private String descripcion;
    private Integer cita_id;
    private  CitaDto cita;
}
