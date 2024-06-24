package io.github.popehiflo.clinica.controller;

import io.github.popehiflo.clinica.dto.TurnoDTO;
import io.github.popehiflo.clinica.entity.Turno;
import io.github.popehiflo.clinica.exception.ResourceNotFoundException;
import io.github.popehiflo.clinica.service.TurnoService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    private static final Logger LOG = Logger.getLogger(TurnoController.class);

    private final TurnoService turnoService;

    public TurnoController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @PostMapping
    public ResponseEntity<TurnoDTO> registrarUnTurno(@RequestBody TurnoDTO turnoDTO) {
        LOG.info("Turno: inicio de registro de un nuevo turno: {}" + turnoDTO);
        return ResponseEntity.ok(turnoService.crearTurno(turnoDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurnoDTO> buscarTurnoPorId(@PathVariable Long id) {
        LOG.info("Turno: inicio de búsqueda de turno por ID: {}" + id);
        return ResponseEntity.ok(turnoService.buscarTurnoPorId(id));
    }

    @PutMapping
    public ResponseEntity<TurnoDTO> actualizarTurno(@RequestBody TurnoDTO turnoDTO) {
        LOG.info("Turno: inicio de actualizar: {}" + turnoDTO);
        TurnoDTO turnoActualizado = turnoService.actualizarTurno(turnoDTO);
        return ResponseEntity.ok(turnoActualizado);
    }

    @GetMapping
    public ResponseEntity<List<TurnoDTO>> listarTodosLosTurnos() {
        LOG.info("Turno: inicio de listar todos los turnos: {}");
        return ResponseEntity.ok(turnoService.listarTodosLosTurnos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Long id) {
        LOG.info("Turno: inicio de eliminar turno con ID: {}" + id);
        try {
            turnoService.eliminarTurno(id);
            return ResponseEntity.ok("Turno eliminado exitosamente");
        } catch (ResourceNotFoundException e) {
            LOG.error("Error al eliminar turno: {}" + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
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
