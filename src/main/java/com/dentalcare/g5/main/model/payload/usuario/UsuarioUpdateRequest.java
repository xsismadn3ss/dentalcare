package com.dentalcare.g5.main.model.payload.usuario;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioUpdateRequest {
    @Nullable
    private String nombre;
    @Nullable
    private String apellido;
    @Nullable
    private String username;
    @Nullable
    private String password;
    @Nullable
    private String email;
    @Nullable
    private String telefono;
}
