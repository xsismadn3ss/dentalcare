package com.dentalcare.g5.main.repository.usuario;

import com.dentalcare.g5.main.model.entity.usuario.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
}
