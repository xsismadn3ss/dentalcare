package com.dentalcare.g5.main.repository.usuario;

import com.dentalcare.g5.main.model.entity.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Query("SELECT u FROM Usuario u WHERE " +
           "(:nombre IS NULL OR LOWER(u.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) AND " +
           "(:apellido IS NULL OR LOWER(u.apellido) LIKE LOWER(CONCAT('%', :apellido, '%'))) AND " +
           "(:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
           "(:telefono IS NULL OR u.telefono LIKE CONCAT('%', :telefono, '%')) AND " +
           "(:rol_id IS NULL OR u.rol.id = :rol_id)")
    List<Usuario> filterUsuarios(@Param("nombre") String nombre,
                                 @Param("apellido") String apellido,
                                 @Param("email") String email,
                                 @Param("telefono") String telefono,
                                 @Param("rol_id") Integer rol_id);

    Optional<Usuario> finByUsername(String username);
}
