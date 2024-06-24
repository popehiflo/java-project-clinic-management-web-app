package io.github.popehiflo.clinica.repository;

import io.github.popehiflo.clinica.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Optional<Paciente> findByCorreo(String correo);
}
