package com.dentalcare.g5.main.model.payload.doctor;

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
public class DoctorCreateRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;
    
    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;
    
    private String noVigilancia;
    
    @NotNull(message = "El ID de la especialidad es obligatorio")
    private Integer especialidadId;
    
    @Valid
    @NotNull(message = "La información del usuario es obligatoria")
    private UsuarioCreateRequest usuario;
}

