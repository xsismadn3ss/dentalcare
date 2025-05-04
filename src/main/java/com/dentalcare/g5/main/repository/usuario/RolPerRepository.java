package com.dentalcare.g5.main.repository.usuario;

import com.dentalcare.g5.main.model.entity.usuario.RolPermiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolPerRepository extends JpaRepository<RolPermiso, Integer> {
    List<RolPermiso> findByPermisoId(Integer permiso_id);
}
