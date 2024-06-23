package io.github.popehiflo.clinica.service;

import io.github.popehiflo.clinica.entity.Odontologo;
import io.github.popehiflo.clinica.entity.Paciente;
import io.github.popehiflo.clinica.entity.Turno;
import io.github.popehiflo.clinica.exception.ResourceNotFoundException;
import io.github.popehiflo.clinica.repository.TurnoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {

    private final TurnoRepository turnoRepository;
    private final PacienteService pacienteService;
    private final OdontologoService odontologoService;

    public TurnoService(TurnoRepository turnoRepository, PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    public Turno crearTurno(Turno turno) {
        Paciente paciente = pacienteService.buscarPacientePorID(turno.getPaciente().getId());
        Odontologo odontologo = odontologoService.buscarOdontologoPorID(turno.getOdontologo().getId());
        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);
        return turnoRepository.save(turno);
    }

    public Turno actualizarTurno(Turno turno) {
        Optional<Turno> turnoBuscado = turnoRepository.findById(turno.getId());
        if (turnoBuscado.isPresent()) {
            Paciente paciente = pacienteService.buscarPacientePorID(turno.getPaciente().getId());
            Odontologo odontologo = odontologoService.buscarOdontologoPorID(turno.getOdontologo().getId());
            turno.setPaciente(paciente);
            turno.setOdontologo(odontologo);
            return turnoRepository.save(turno);
        } else {
            throw new ResourceNotFoundException("Turno no encontrado");
        }
    }

    public Turno buscarTurnoPorId(Long id) {
        return turnoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado"));
    }

    public void eliminarTurno(Long id) {
        Optional<Turno> turnoBuscado = turnoRepository.findById(id);
        if (turnoBuscado.isPresent()) {
            turnoRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Turno no encontrado");
        }
    }

    public List<Turno> listarTodosLosTurnos() {
        return turnoRepository.findAll();
    }

    public boolean existeTurnoParaOdontologo(Long odontologoId) {
        return turnoRepository.existsByOdontologo_Id(odontologoId);
    }

    public boolean existeTurnoParaPaciente(Long pacienteId) {
        return turnoRepository.existsByPaciente_Id(pacienteId);
    }
}
