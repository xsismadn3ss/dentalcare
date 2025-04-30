package com.dentalcare.g5.main.model.dto.doctor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EspecialidadDto {
    private  Integer id;
    private  String name;
    private List<DoctorDto> doctores;
}
