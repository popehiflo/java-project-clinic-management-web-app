package io.github.popehiflo.clinica.repository;

import io.github.popehiflo.clinica.entity.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OdontologoRepository extends JpaRepository<Odontologo, Long> {

    Optional<Odontologo> findByMatricula(String matricula);
}
