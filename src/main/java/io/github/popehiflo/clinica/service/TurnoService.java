package io.github.popehiflo.clinica.service;

import io.github.popehiflo.clinica.entity.Turno;
import io.github.popehiflo.clinica.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {

    @Autowired
    private TurnoRepository turnoRepository;

    public Turno crearTurno(Turno turno) {
        return turnoRepository.save(turno);
    }

    public void actualizarTurno(Turno turno) {
        turnoRepository.save(turno);
    }

    public Optional<Turno> buscarTurnoPorId(Long id) {
        return turnoRepository.findById(id);
    }

    public void eliminarTurno(Long id) {
        turnoRepository.deleteById(id);
    }

    public List<Turno> listarTodosLosTurnos() {
        return turnoRepository.findAll();
    }


}
