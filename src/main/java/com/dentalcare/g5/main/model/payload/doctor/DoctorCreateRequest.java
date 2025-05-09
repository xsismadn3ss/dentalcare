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

    @NotBlank(message = "El No. de vigilancia es obligatorio")
    private String no_vigiliancia;
    
    @NotNull(message = "El ID de la especialidad es obligatorio")
    private Integer especialidad_id;

    @NotNull(message = "El ID de usuario es obligatorio")
    private Integer usuario_id;
    /**@Valid
    @NotNull(message = "La información del usuario es obligatoria")
    private UsuarioCreateRequest usuario;**/
}

