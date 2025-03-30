package com.dentalcare.g5.main.repository.doctor;

import com.dentalcare.g5.main.model.entity.doctor.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
}
