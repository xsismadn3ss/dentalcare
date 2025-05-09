package com.dentalcare.g5.main.repository;

import com.dentalcare.g5.main.model.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    Optional<Paciente> findByUsuarioId(Integer usuario_id);
}
