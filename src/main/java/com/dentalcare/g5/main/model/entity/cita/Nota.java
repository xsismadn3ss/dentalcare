package com.dentalcare.g5.main.model.entity.cita;

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
public class Nota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titulo;
    private String descripcion;
    @ManyToOne
    @JoinColumn(name = "cita_id", referencedColumnName = "id")
    private Cita cita;
    
}
