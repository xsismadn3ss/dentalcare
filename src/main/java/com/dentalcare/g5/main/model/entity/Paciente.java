package com.dentalcare.g5.main.model.entity;

import com.dentalcare.g5.main.model.entity.cita.Cita;
import com.dentalcare.g5.main.model.entity.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate fechaRegistro;

    @OneToOne()
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;
    private Integer usuario_id;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cita> citas = new ArrayList<>();
}
