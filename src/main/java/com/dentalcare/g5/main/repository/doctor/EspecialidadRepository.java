package com.dentalcare.g5.main.repository.doctor;

import com.dentalcare.g5.main.model.entity.doctor.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecialidadRepository extends JpaRepository<Especialidad, Integer> {
}
