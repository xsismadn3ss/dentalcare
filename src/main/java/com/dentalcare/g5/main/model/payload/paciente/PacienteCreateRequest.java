package com.dentalcare.g5.main.model.payload.paciente;

import com.dentalcare.g5.main.model.payload.usuario.UsuarioCreateRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteCreateRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;
    
    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;
    
    private String direccion;
    
    @Valid
    @NotNull(message = "La información del usuario es obligatoria")
    private UsuarioCreateRequest usuario;
}

