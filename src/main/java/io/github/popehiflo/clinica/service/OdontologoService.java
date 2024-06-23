package io.github.popehiflo.clinica.service;

import io.github.popehiflo.clinica.entity.Odontologo;
import io.github.popehiflo.clinica.exception.DataIntegrityViolationException;
import io.github.popehiflo.clinica.exception.ResourceNotFoundException;
import io.github.popehiflo.clinica.repository.OdontologoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {

    private final OdontologoRepository odontologoRepository;
    private final TurnoService turnoService;

    public OdontologoService(OdontologoRepository odontologoRepository, TurnoService turnoService) {
        this.odontologoRepository = odontologoRepository;
        this.turnoService = turnoService;
    }

    public Odontologo crearOdontologo(Odontologo odontologo) {
        return odontologoRepository.save(odontologo);
    }

    public Odontologo actualizarOdontologo(Odontologo odontologo) {
        Optional<Odontologo> odontologoBuscado = odontologoRepository.findById(odontologo.getId());
        if (odontologoBuscado.isPresent()) {
            return odontologoRepository.save(odontologo);
        } else {
            throw new ResourceNotFoundException("Odontólogo no encontrado");
        }
    }

    public Odontologo buscarOdontologoPorID(Long id) {
        return odontologoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Odontólogo no encontrado"));
    }

    public Odontologo buscarOdontologoPorMatricula(String matricula) {
        return odontologoRepository.findByMatricula(matricula)
                .orElseThrow(() -> new ResourceNotFoundException("Odontólogo no encontrado"));
    }

    public void eliminarOdontologo(Long id) {
        Optional<Odontologo> odontologoBuscado = odontologoRepository.findById(id);
        if (odontologoBuscado.isPresent()) {
            if (turnoService.existeTurnoParaOdontologo(id)) {
                throw new DataIntegrityViolationException("No se puede eliminar el odontólogo porque tiene citas asignadas");
            }
            odontologoRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Odontólogo no encontrado");
        }
    }

    public List<Odontologo> listarTodosLosOdontologos() {
        return odontologoRepository.findAll();
    }

}
