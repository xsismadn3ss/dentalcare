package com.dentalcare.g5.main.repository.cita;

import com.dentalcare.g5.main.model.entity.cita.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {
    List<Cita> getByDoctorId(Integer doctorId);
    List<Cita> getByPacienteId(Integer pacienteId);
}
