package io.github.popehiflo.clinica.controller;

import io.github.popehiflo.clinica.entity.Paciente;
import io.github.popehiflo.clinica.service.PacienteService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private static final Logger LOG = Logger.getLogger(PacienteController.class);

    @Autowired
    private PacienteService pacienteService;


    @PostMapping
    public ResponseEntity<Paciente> registrarUnPaciente(@RequestBody Paciente paciente) {
        LOG.info("Paciente: inicio de registro de nuevo paciente: {}");
        return ResponseEntity.ok(pacienteService.crearPaciente(paciente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPacientePorID(@PathVariable Long id) {
        LOG.info("Paciente: inicio de búsqueda de paciente por ID: {}");
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorID(id);
        if (pacienteBuscado.isPresent()) {
            return ResponseEntity.ok(pacienteBuscado.get());
        } else {
            //TODO: Aquí deberíamos lanzar una excepción ResourceNotFoundException o ObjectNotFoundException
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<String> actualizarPaciente(@RequestBody Paciente paciente) {
        LOG.info("Paciente: inicio de actualizar: {}");
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorID(paciente.getId());
        if (pacienteBuscado.isPresent()) {
            pacienteService.actualizarPaciente(paciente);
            return ResponseEntity.ok("paciente actualizado");
        } else {
            return ResponseEntity.badRequest().body("no se encontró el paciente");
        }

    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<Paciente> buscarPacientePorCorreo(@PathVariable String correo) {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorCorreo(correo);
        if (pacienteBuscado.isPresent()) {
            return ResponseEntity.ok(pacienteBuscado.get());
        } else {
            //TODO: Aquí deberíamos lanzar una excepción ResourceNotFoundException o ObjectNotFoundException
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listarTodosLosPacientes() {
        LOG.info("Paciente: inicio de listar todos: {}");
        return ResponseEntity.ok(pacienteService.listarTodosLosPacientes());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Long id) {
        LOG.info("Paciente: inicio de eliminar paciente con ID: {}");
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPacientePorID(id);
        if (pacienteBuscado.isPresent()) {
            pacienteService.eliminarPaciente(id);
            return ResponseEntity.ok("paciente eliminado con exitosamente");
        } else {
            //TODO: Aquí deberíamos lanzar una excepción ResourceNotFoundException o ObjectNotFoundException
            return ResponseEntity.notFound().build();
        }
    }
}
