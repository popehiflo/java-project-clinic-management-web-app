package io.github.popehiflo.clinica.service;

import io.github.popehiflo.clinica.controller.TurnoController;
import io.github.popehiflo.clinica.dto.TurnoDTO;
import io.github.popehiflo.clinica.entity.Odontologo;
import io.github.popehiflo.clinica.entity.Paciente;
import io.github.popehiflo.clinica.entity.Turno;
import io.github.popehiflo.clinica.exception.ResourceNotFoundException;
import io.github.popehiflo.clinica.repository.TurnoRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TurnoService {
    private static final Logger LOG = Logger.getLogger(TurnoService.class);

    private final TurnoRepository turnoRepository;
    private final PacienteService pacienteService;
    private final OdontologoService odontologoService;

    public TurnoService(TurnoRepository turnoRepository, PacienteService pacienteService, OdontologoService odontologoService) {
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
    }

    public TurnoDTO crearTurno(TurnoDTO turnoDTO) {
        LOG.info ("Creando nuevo turno: {}" + turnoDTO);
        Paciente paciente = pacienteService.buscarPacientePorID(turnoDTO.getPacienteId());
        Odontologo odontologo = odontologoService.buscarOdontologoPorID(turnoDTO.getOdontologoId());
        Turno turno = new Turno();
        turno.setPaciente(paciente);
        turno.setOdontologo(odontologo);
        turno.setFecha(turnoDTO.getFecha());
        Turno nuevoTurno = turnoRepository.save(turno);
        LOG.info("Turno creado: {}" + nuevoTurno);
        return mapearATurnoDTO(nuevoTurno);
    }

    public TurnoDTO actualizarTurno(TurnoDTO turnoDTO) {
        LOG.info("Actualizando turno: {}" + turnoDTO);
        Optional<Turno> turnoBuscado = turnoRepository.findById(turnoDTO.getId());
        if (turnoBuscado.isPresent()) {
            Paciente paciente = pacienteService.buscarPacientePorID(turnoDTO.getPacienteId());
            Odontologo odontologo = odontologoService.buscarOdontologoPorID(turnoDTO.getOdontologoId());
            Turno turno = turnoBuscado.get();
            turno.setPaciente(paciente);
            turno.setOdontologo(odontologo);
            turno.setFecha(turnoDTO.getFecha());
            Turno turnoActualizado = turnoRepository.save(turno);
            LOG.info("Turno actualizado: {} " + turnoActualizado);
            return mapearATurnoDTO(turnoActualizado);
        } else {
            throw new ResourceNotFoundException("Turno no encontrado");
        }
    }

    public TurnoDTO buscarTurnoPorId(Long id) {
        LOG.info("Buscando turno por ID: {} " + id);
        Turno turnoBuscado = turnoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado"));
        LOG.info("Turno encontrado: {} " + turnoBuscado);
        return mapearATurnoDTO(turnoBuscado);
    }

    public void eliminarTurno(Long id) {
        LOG.info("Eliminando turno con ID: {}" + id);
        Optional<Turno> turnoBuscado = turnoRepository.findById(id);
        if (turnoBuscado.isPresent()) {
            turnoRepository.deleteById(id);
            LOG.info("Turno eliminado");
        } else {
            throw new ResourceNotFoundException("Turno no encontrado");
        }
    }

    public List<TurnoDTO> listarTodosLosTurnos() {
        LOG.info("Listando todos los turnos");
        List<Turno> turnos = turnoRepository.findAll();
        LOG.info("Se encontraron {} turnos " + turnos.size());
        return turnos.stream()
                .map(this::mapearATurnoDTO)
                .collect(Collectors.toList());
    }

    private TurnoDTO mapearATurnoDTO(Turno turno) {
        TurnoDTO turnoDTO = new TurnoDTO();
        turnoDTO.setId(turno.getId());
        turnoDTO.setPacienteId(turno.getPaciente().getId());
        turnoDTO.setOdontologoId(turno.getOdontologo().getId());
        turnoDTO.setFecha(turno.getFecha());
        return turnoDTO;
    }

}
