package io.github.popehiflo.clinica.controller;

import io.github.popehiflo.clinica.entity.Odontologo;
import io.github.popehiflo.clinica.exception.DataIntegrityViolationException;
import io.github.popehiflo.clinica.exception.ResourceNotFoundException;
import io.github.popehiflo.clinica.service.OdontologoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    private static final Logger LOG = Logger.getLogger(OdontologoController.class);

    private final OdontologoService odontologoService;

    public OdontologoController(OdontologoService odontologoService) {
        this.odontologoService = odontologoService;
    }

    @PostMapping
    public ResponseEntity<Odontologo> registrarUnOdontologo(@RequestBody Odontologo odontologo) {
        LOG.info("Odontologo: inicio de registro de nuevo odontologo: {}");
        return ResponseEntity.ok(odontologoService.crearOdontologo(odontologo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarOdontologoPorID(@PathVariable Long id) {
        LOG.info("Odontologo: inicio de búsqueda de odontologo: {}");
        Odontologo odontologoBuscado = odontologoService.buscarOdontologoPorID(id);
        return ResponseEntity.ok(odontologoBuscado);
    }

    @PutMapping
    public ResponseEntity<Odontologo> actualizarOdontologo(@RequestBody Odontologo odontologo) {
        LOG.info("Odontologo: inicio de actualizar: {}");
        Odontologo odontologoActualizado = odontologoService.actualizarOdontologo(odontologo);
        return ResponseEntity.ok(odontologoActualizado);
    }

    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<Odontologo> buscarPacientePorMatricula(@PathVariable String matricula) {
        Odontologo odontologoBuscado = odontologoService.buscarOdontologoPorMatricula(matricula);
        return ResponseEntity.ok(odontologoBuscado);
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> listarTodosLosOdontologos() {
        LOG.info("Odontologo: inicio de listar todos: {}");
        return ResponseEntity.ok(odontologoService.listarTodosLosOdontologos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Long id) {
        LOG.info("Odontologo: inicio de eliminar paciente con ID: {}");
        try {
            odontologoService.eliminarOdontologo(id);
            return ResponseEntity.ok("Odontólogo eliminado exitosamente");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
