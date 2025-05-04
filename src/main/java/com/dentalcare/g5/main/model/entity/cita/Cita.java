package com.dentalcare.g5.main.model.entity.cita;

import com.dentalcare.g5.main.model.entity.Paciente;
import com.dentalcare.g5.main.model.entity.doctor.Doctor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate fecha;
    private Time hora;
    private String motivo;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
    private Integer doctor_id;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;
    private Integer paciente_id;

    @OneToMany(mappedBy = "cita", cascade = CascadeType.ALL)
    private List<Nota> notas = new ArrayList<>();

    @OneToMany(mappedBy = "cita", cascade = CascadeType.ALL)
    private List<Tratamiento> tratamientos = new ArrayList<>();
}
