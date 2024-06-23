package io.github.popehiflo.clinica.controller;

import io.github.popehiflo.clinica.entity.Odontologo;
import io.github.popehiflo.clinica.entity.Paciente;
import io.github.popehiflo.clinica.entity.Turno;
import io.github.popehiflo.clinica.service.OdontologoService;
import io.github.popehiflo.clinica.service.PacienteService;
import io.github.popehiflo.clinica.service.TurnoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController {

    private static final Logger LOG = Logger.getLogger(PacienteController.class);

    @Autowired
    private TurnoService turnoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;

    @PostMapping
    public ResponseEntity<Turno> registrarUnTurno(@RequestBody Turno turno) {
        LOG.info("Turno: inicio de registro de un nuevo turno: {}");
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorID(turno.getPaciente().getId());
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoPorID(turno.getOdontologo().getId());

        if (pacienteBuscado.isPresent() && odontologoBuscado.isPresent()) {
            turno.setPaciente(pacienteBuscado.get());
            turno.setOdontologo(odontologoBuscado.get());
            return ResponseEntity.ok(turnoService.crearTurno(turno));
        } else {
            //TODO: Aquí deberíamos lanzar una excepción ResourceNotFoundException o ObjectNotFoundException
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turno> buscarTurnoPorId(@PathVariable Long id) {
        LOG.info("Turno: inicio de búsqueda de turno por ID: {}");
        Optional<Turno> turnoBuscado = turnoService.buscarTurnoPorId(id);
        if (turnoBuscado.isPresent()) {
            return ResponseEntity.ok(turnoBuscado.get());
        } else {
            //TODO: Aquí deberíamos lanzar una excepción ResourceNotFoundException o ObjectNotFoundException
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Turno>> listarTodosLosTurnos() {
        LOG.info("Turno: inicio de listar todos los turnos: {}");
        return ResponseEntity.ok(turnoService.listarTodosLosTurnos());
    }
}
