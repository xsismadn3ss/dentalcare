package com.dentalcare.g5.main.repository.cita;

import com.dentalcare.g5.main.model.entity.cita.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Integer> {
    List<Nota> findByCitaId(int id);
}
