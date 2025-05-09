package com.dentalcare.g5.main.model.payload.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolCreateRequest {
    private String nombre;
}
