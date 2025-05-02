package com.dentalcare.g5.main.repository.usuario;

import com.dentalcare.g5.main.model.entity.usuario.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Integer> {
}
