package com.dentalcare.g5.main.repository.usuario;

import com.dentalcare.g5.main.model.entity.usuario.RolPermiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolPerRepository extends JpaRepository<RolPermiso, Integer> {
}
