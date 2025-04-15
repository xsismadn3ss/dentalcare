package com.dentalcare.g5.main.model.dto.cita;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TratamientoDto {
    private Integer id;
    private String Nombre;
    private boolean pendiente;
    private  Integer cita_id;
    private CitaDto cita;
}
