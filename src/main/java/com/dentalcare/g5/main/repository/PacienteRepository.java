package com.dentalcare.g5.main.repository;

import com.dentalcare.g5.main.model.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
}
