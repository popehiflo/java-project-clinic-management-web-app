package io.github.popehiflo.clinica.repository;

import io.github.popehiflo.clinica.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurnoRepository extends JpaRepository<Turno, Long> {

    boolean existsByOdontologo_Id(Long odontologoId);

    boolean existsByPaciente_Id(Long pacienteId);
}
