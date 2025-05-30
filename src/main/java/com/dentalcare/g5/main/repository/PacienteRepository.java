package com.dentalcare.g5.main.repository;

import com.dentalcare.g5.main.model.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    Optional<Paciente> findByUsuarioId(Integer usuarioId);

    @Query("SELECT p FROM Paciente p LEFT JOIN p.usuario u WHERE " +
           "(:id IS NULL OR p.id = :id) AND " +
           "(:usuario_id IS NULL OR u.id = :usuario_id) AND " +
           "(:fechaRegistroDesde IS NULL OR p.fechaRegistro >= :fechaRegistroDesde) AND " +
           "(:fechaRegistroHasta IS NULL OR p.fechaRegistro <= :fechaRegistroHasta) AND " +
           "(:nombre IS NULL OR LOWER(u.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) AND " +
           "(:apellido IS NULL OR LOWER(u.apellido) LIKE LOWER(CONCAT('%', :apellido, '%'))) AND " +
           "(:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
           "(:telefono IS NULL OR u.telefono LIKE CONCAT('%', :telefono, '%'))")
    List<Paciente> filterPacientes(@Param("id") Integer id,
                                   @Param("usuario_id") Integer usuario_id,
                                   @Param("fechaRegistroDesde") LocalDate fechaRegistroDesde,
                                   @Param("fechaRegistroHasta") LocalDate fechaRegistroHasta,
                                   @Param("nombre") String nombre,
                                   @Param("apellido") String apellido,
                                   @Param("email") String email,
                                   @Param("telefono") String telefono);
}
