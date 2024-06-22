package io.github.popehiflo.clinica.service;

import io.github.popehiflo.clinica.entity.Paciente;
import io.github.popehiflo.clinica.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente crearPaciente(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public void actualizarPaciente(Paciente paciente) {
        pacienteRepository.save(paciente);
    }

    public Optional<Paciente> buscarPacientePorID(Long id) {
        return pacienteRepository.findById(id);
    }

    public Optional<Paciente> buscarPacientePorCorreo(String correo) {
        return pacienteRepository.findByCorreo(correo);
    }

    public void eliminarPaciente(Long id) {
        pacienteRepository.deleteById(id);
    }

    public List<Paciente> listarTodosLosPacientes() {
        return pacienteRepository.findAll();
    }
}
