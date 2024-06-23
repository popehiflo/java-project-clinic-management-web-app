package io.github.popehiflo.clinica.controller;

import io.github.popehiflo.clinica.entity.Turno;
import io.github.popehiflo.clinica.exception.ResourceNotFoundException;
import io.github.popehiflo.clinica.service.TurnoService;
import org.apache.log4j.Logger;
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
    public ResponseEntity<Turno> registrarUnTurno(@RequestBody Turno turno) {
        LOG.info("Turno: inicio de registro de un nuevo turno: {}");
        return ResponseEntity.ok(turnoService.crearTurno(turno));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscarTurnoPorId(@PathVariable Long id) {
        LOG.info("Turno: inicio de búsqueda de turno por ID: {}");
        return ResponseEntity.ok(turnoService.buscarTurnoPorId(id));
    }

    @PutMapping
    public ResponseEntity<Turno> actualizarTurno(@RequestBody Turno turno) {
        LOG.info("Turno: inicio de actualizar: {}");
        Turno turnoActualizado = turnoService.actualizarTurno(turno);
        return ResponseEntity.ok(turnoActualizado);
    }

    @GetMapping
    public ResponseEntity<List<Turno>> listarTodosLosTurnos() {
        LOG.info("Turno: inicio de listar todos los turnos: {}");
        return ResponseEntity.ok(turnoService.listarTodosLosTurnos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Long id) {
        LOG.info("Turno: inicio de eliminar turno con ID: {}");
        try {
            turnoService.eliminarTurno(id);
            return ResponseEntity.ok("Turno eliminado exitosamente");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
