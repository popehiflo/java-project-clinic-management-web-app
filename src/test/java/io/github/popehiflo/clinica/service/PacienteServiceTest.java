package io.github.popehiflo.clinica.service;

import io.github.popehiflo.clinica.entity.Domicilio;
import io.github.popehiflo.clinica.entity.Paciente;
import io.github.popehiflo.clinica.exception.DataIntegrityViolationException;
import io.github.popehiflo.clinica.exception.ResourceNotFoundException;
import io.github.popehiflo.clinica.repository.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private TurnoValidationService turnoValidationService;

    @InjectMocks
    private PacienteService pacienteService;

    private Paciente paciente;
    private final Long pacienteId = 1L;

    @BeforeEach
    public void setUp() {
        Domicilio domicilio = new Domicilio("Calle", 123, "Ciudad", "Provincia");
        paciente = new Paciente("Pool", "Pérez", "12345678", LocalDate.now(), domicilio, "popehiflo@example.com");
        paciente.setId(pacienteId);
    }

    @Test
    public void crearPacienteTest() {
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(paciente);
        Paciente pacienteGuardado = pacienteService.crearPaciente(paciente);
        assertNotNull(pacienteGuardado);
        verify(pacienteRepository).save(any(Paciente.class));
    }

    @Test
    public void buscarPacientePorIDTest() {
        when(pacienteRepository.findById(anyLong())).thenReturn(Optional.of(paciente));
        Paciente pacienteBuscado = pacienteService.buscarPacientePorID(pacienteId);
        assertNotNull(pacienteBuscado);
        verify(pacienteRepository).findById(pacienteId);
    }

    @Test
    public void buscarPacientePorIDNoEncontradoTest() {
        when(pacienteRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> pacienteService.buscarPacientePorID(pacienteId));
    }

    @Test
    public void actualizarPacienteTest() {
        when(pacienteRepository.findById(anyLong())).thenReturn(Optional.of(paciente));
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(paciente);

        paciente.setApellido("González");
        Paciente resultado = pacienteService.actualizarPaciente(paciente);

        assertNotNull(resultado);
        assertEquals("González", resultado.getApellido());

        verify(pacienteRepository).findById(pacienteId);
        verify(pacienteRepository).save(paciente);
    }

    @Test
    public void actualizarPacienteNoEncontradoTest() {
        when(pacienteRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> pacienteService.actualizarPaciente(paciente));
    }

    @Test
    public void buscarPacientePorCorreoTest() {
        when(pacienteRepository.findByCorreo(anyString())).thenReturn(Optional.of(paciente));
        Paciente pacienteBuscado = pacienteService.buscarPacientePorCorreo("popehiflo@example.com");
        assertNotNull(pacienteBuscado);
        verify(pacienteRepository).findByCorreo("popehiflo@example.com");
    }

    @Test
    public void buscarPacientePorCorreoNoEncontradoTest() {
        when(pacienteRepository.findByCorreo(anyString())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> pacienteService.buscarPacientePorCorreo("popehiflo@example.com"));
    }

    @Test
    public void eliminarPacienteTest() {
        when(pacienteRepository.findById(anyLong())).thenReturn(Optional.of(paciente));
        when(turnoValidationService.existeTurnoParaPaciente(anyLong())).thenReturn(false);
        doNothing().when(pacienteRepository).deleteById(anyLong());

        pacienteService.eliminarPaciente(pacienteId);

        verify(pacienteRepository).findById(pacienteId);
        verify(turnoValidationService).existeTurnoParaPaciente(pacienteId);
        verify(pacienteRepository).deleteById(pacienteId);
    }

    @Test
    public void eliminarPacienteNoEncontradoTest() {
        when(pacienteRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pacienteService.eliminarPaciente(pacienteId));
        verify(pacienteRepository).findById(pacienteId);
        verify(turnoValidationService, never()).existeTurnoParaPaciente(anyLong());
        verify(pacienteRepository, never()).deleteById(anyLong());
    }

    @Test
    public void eliminarPacienteConTurnosTest() {
        when(pacienteRepository.findById(anyLong())).thenReturn(Optional.of(paciente));
        when(turnoValidationService.existeTurnoParaPaciente(anyLong())).thenReturn(true);

        assertThrows(DataIntegrityViolationException.class, () -> pacienteService.eliminarPaciente(pacienteId));
        verify(pacienteRepository).findById(pacienteId);
        verify(turnoValidationService).existeTurnoParaPaciente(pacienteId);
        verify(pacienteRepository, never()).deleteById(anyLong());
    }

    @Test
    public void listarTodosLosPacientesTest() {
        Paciente otroPaciente = new Paciente("Petter", "López", "87654321", LocalDate.now(), new Domicilio(), "maria@example.com");
        List<Paciente> pacientes = Arrays.asList(paciente, otroPaciente);
        when(pacienteRepository.findAll()).thenReturn(pacientes);

        List<Paciente> resultado = pacienteService.listarTodosLosPacientes();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(pacienteRepository).findAll();
    }
}
