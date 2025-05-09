package com.dentalcare.g5.main.model.payload.doctor;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EspecialidadCreateRequest {
    @NotBlank(message = "El nombre de id es requerido")
    private String name;
}
