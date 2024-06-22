package io.github.popehiflo.clinica.service;

import io.github.popehiflo.clinica.entity.Odontologo;
import io.github.popehiflo.clinica.repository.OdontologoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {

    @Autowired
    private OdontologoRepository odontologoRepository;

    public Odontologo crearOdontologo(Odontologo odontologo) {
        return odontologoRepository.save(odontologo);
    }

    public void actualizarOdontologo(Odontologo odontologo) {
        odontologoRepository.save(odontologo);
    }

    public Optional<Odontologo> buscarOdontologoPorID(Long id) {
        return odontologoRepository.findById(id);
    }

    public Optional<Odontologo> buscarOdontologoPorMatricula(String matricula) {
        return odontologoRepository.findByMatricula(matricula);
    }

    public void eliminarOdontologo(Long id) {
        odontologoRepository.deleteById(id);
    }

    public List<Odontologo> listarTodosLosOdontologos() {
        return odontologoRepository.findAll();
    }

}
