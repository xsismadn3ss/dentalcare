package com.dentalcare.g5.main.model.entity.usuario;

import com.dentalcare.g5.main.model.entity.Paciente;
import com.dentalcare.g5.main.model.entity.doctor.Doctor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String apellido;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String telefono;

    private String username;
    private String password;

    @OneToOne()
    @JoinColumn(name = "rol_id", referencedColumnName = "id")
    private Rol rol;

    @OneToOne(cascade = CascadeType.ALL)
    private Paciente paciente;

    @OneToOne(cascade = CascadeType.ALL)
    private Doctor doctor;
}
