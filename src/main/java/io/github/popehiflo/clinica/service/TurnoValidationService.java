package io.github.popehiflo.clinica.service;

import io.github.popehiflo.clinica.repository.TurnoRepository;
import org.springframework.stereotype.Service;

@Service
public class TurnoValidationService {

    private final TurnoRepository turnoRepository;

    public TurnoValidationService(TurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    public boolean existeTurnoParaPaciente(Long pacienteId) {
        return turnoRepository.existsByPaciente_Id(pacienteId);
    }

    public boolean existeTurnoParaOdontologo(Long odontologoId) {
        return turnoRepository.existsByOdontologo_Id(odontologoId);
    }
}
