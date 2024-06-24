package io.github.popehiflo.clinica.controller;

import io.github.popehiflo.clinica.entity.Paciente;
import io.github.popehiflo.clinica.exception.DataIntegrityViolationException;
import io.github.popehiflo.clinica.exception.ResourceNotFoundException;
import io.github.popehiflo.clinica.service.PacienteService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private static final Logger LOG = Logger.getLogger(PacienteController.class);

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }


    @PostMapping
    public ResponseEntity<Paciente> registrarUnPaciente(@RequestBody Paciente paciente) {
        LOG.info("Paciente: inicio de registro de nuevo paciente: {}");
        return ResponseEntity.ok(pacienteService.crearPaciente(paciente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPacientePorID(@PathVariable Long id) {
        LOG.info("Paciente: inicio de búsqueda de paciente por ID: {}");
        Paciente pacienteBuscado = pacienteService.buscarPacientePorID(id);
        return ResponseEntity.ok(pacienteBuscado);
    }

    @PutMapping
    public ResponseEntity<Paciente> actualizarPaciente(@RequestBody Paciente paciente) {
        LOG.info("Paciente: inicio de actualizar: {}");
        Paciente pacienteActualizado = pacienteService.actualizarPaciente(paciente);
        return ResponseEntity.ok(pacienteActualizado);
    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<Paciente> buscarPacientePorCorreo(@PathVariable String correo) {
        Paciente pacienteBuscado = pacienteService.buscarPacientePorCorreo(correo);
        return ResponseEntity.ok(pacienteBuscado);
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listarTodosLosPacientes() {
        LOG.info("Paciente: inicio de listar todos: {}");
        return ResponseEntity.ok(pacienteService.listarTodosLosPacientes());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Long id) throws ResourceNotFoundException {
        LOG.info("Paciente: inicio de eliminar paciente con ID: {}");
        try {
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.ok("Paciente eliminado exitosamente");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
