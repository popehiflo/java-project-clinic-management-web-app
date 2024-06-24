package io.github.popehiflo.clinica.service;

import io.github.popehiflo.clinica.entity.Paciente;
import io.github.popehiflo.clinica.exception.DataIntegrityViolationException;
import io.github.popehiflo.clinica.exception.ResourceNotFoundException;
import io.github.popehiflo.clinica.repository.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final TurnoValidationService turnoValidationService;

    public PacienteService(PacienteRepository pacienteRepository, TurnoValidationService turnoValidationService) {
        this.pacienteRepository = pacienteRepository;
        this.turnoValidationService = turnoValidationService;
    }

    public Paciente crearPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public Paciente actualizarPaciente(Paciente paciente) {
        Optional<Paciente> pacienteBuscado = pacienteRepository.findById(paciente.getId());
        if (pacienteBuscado.isPresent()) {
            return pacienteRepository.save(paciente);
        } else {
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
    }

    public Paciente buscarPacientePorID(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));
    }

    public Paciente buscarPacientePorCorreo(String correo) {
        return pacienteRepository.findByCorreo(correo)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));
    }

    public void eliminarPaciente(Long id) {
        Optional<Paciente> pacienteBuscado = pacienteRepository.findById(id);
        if (pacienteBuscado.isPresent()) {
            if (turnoValidationService.existeTurnoParaPaciente(id)) {
                throw new DataIntegrityViolationException("No se puede eliminar el paciente porque tiene turnos asignados");
            }
            pacienteRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
    }

    public List<Paciente> listarTodosLosPacientes() {
        return pacienteRepository.findAll();
    }
}
