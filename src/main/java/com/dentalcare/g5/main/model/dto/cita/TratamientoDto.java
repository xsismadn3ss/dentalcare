package com.dentalcare.g5.main.model.dto.cita;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TratamientoDto {
    private Integer id;
    private String nombre;
    private boolean pendiente;
    private CitaDto cita;
}
