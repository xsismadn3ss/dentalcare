package com.dentalcare.g5.main.repository.cita;

import com.dentalcare.g5.main.model.entity.cita.Tratamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TratamientoRepository extends JpaRepository<Tratamiento, Integer> {
}
