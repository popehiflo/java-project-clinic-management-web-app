package io.github.popehiflo.clinica.controller;

import io.github.popehiflo.clinica.entity.Odontologo;
import io.github.popehiflo.clinica.service.OdontologoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    private static final Logger LOG = Logger.getLogger(OdontologoController.class);

    @Autowired
    private OdontologoService odontologoService;

    @PostMapping
    public ResponseEntity<Odontologo> registrarUnOdontologo(@RequestBody Odontologo odontologo) {
        LOG.info("Odontologo: inicio de registro de nuevo odontologo: {}");
        return ResponseEntity.ok(odontologoService.crearOdontologo(odontologo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarOdontologoPorID(@PathVariable Long id) {
        LOG.info("Odontologo: inicio de búsqueda de odontologo: {}");
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoPorID(id);
        if (odontologoBuscado.isPresent()) {
            return ResponseEntity.ok(odontologoBuscado.get());
        } else {
            //TODO: Aquí deberíamos lanzar una excepción ResourceNotFoundException / ObjectNotFoundException
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<String> actualizarOdontologo(@RequestBody Odontologo odontologo) {
        LOG.info("Odontologo: inicio de actualizar: {}");
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoPorID(odontologo.getId());
        if (odontologoBuscado.isPresent()) {
            odontologoService.actualizarOdontologo(odontologo);
            return ResponseEntity.ok("odontólogo actualizado");
        } else {
            //TODO: Aquí deberíamos lanzar una excepción ResourceNotFoundException / ObjectNotFoundException
            return ResponseEntity.badRequest().body("no se encontró el odontólogo");
        }
    }

    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<Odontologo> buscarPacientePorMatricula(@PathVariable String matricula) {
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoPorMatricula(matricula);
        if (odontologoBuscado.isPresent()) {
            return ResponseEntity.ok(odontologoBuscado.get());
        } else {
            //TODO: Aquí deberíamos lanzar una excepción ResourceNotFoundException o ObjectNotFoundException
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> listarTodosLosOdontologos() {
        LOG.info("Odontologo: inicio de listar todos: {}");
        return ResponseEntity.ok(odontologoService.listarTodosLosOdontologos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Long id) {
        LOG.info("Odontologo: inicio de eliminar paciente con ID: {}");
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarOdontologoPorID(id);
        if (odontologoBuscado.isPresent()) {
            odontologoService.eliminarOdontologo(id);
            return ResponseEntity.ok("odontólogo eliminado con exitosamente");
        } else {
            //TODO: Aquí deberíamos lanzar una excepción ResourceNotFoundException o ObjectNotFoundException
            return ResponseEntity.notFound().build();
        }
    }
}
