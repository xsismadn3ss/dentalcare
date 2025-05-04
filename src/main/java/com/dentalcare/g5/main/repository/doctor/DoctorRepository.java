package com.dentalcare.g5.main.repository.doctor;

import com.dentalcare.g5.main.model.entity.doctor.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Optional<Doctor> findByUsuarioId(Integer usuario_id);
}
