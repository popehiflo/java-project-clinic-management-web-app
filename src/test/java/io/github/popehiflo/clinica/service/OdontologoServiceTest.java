package io.github.popehiflo.clinica.service;

import io.github.popehiflo.clinica.entity.Odontologo;
import io.github.popehiflo.clinica.exception.DataIntegrityViolationException;
import io.github.popehiflo.clinica.exception.ResourceNotFoundException;
import io.github.popehiflo.clinica.repository.OdontologoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/* Evita @SpringBootTest para pruebas unitarias: '@SpringBootTest' carga todo el contexto de la aplicación,
 * lo cual es innecesario y lento para pruebas unitarias. En su lugar, usa @ExtendWith(MockitoExtension.class)
   para permitir el uso de mocks.*/
@ExtendWith(MockitoExtension.class)
public class OdontologoServiceTest {

    /* Usa mocks para las dependencias: En lugar de inyectar OdontologoService con @Autowired, crea un mock para
     * OdontologoRepository y cualquier otra dependencia externa que OdontologoService pueda tener. */
    @Mock
    private OdontologoRepository odontologoRepository;

    @Mock
    private TurnoValidationService turnoValidationService;

    @InjectMocks
    private OdontologoService odontologoService;

    private Odontologo odontologo;
    private Long odontologoId = 1L;

    @BeforeEach
    public void setUp() {
        odontologo = new Odontologo("00000", "Pool", "Hijuela");
        odontologo.setId(odontologoId);
    }

    /* Evita el orden de las pruebas: Las pruebas unitarias deben ser independientes y poder ejecutarse en cualquier
     * orden. Si necesitas un orden específico, es una señal de que las pruebas no están aisladas correctamente. */

    /* Maneja los datos de prueba adecuadamente: En lugar de asumir IDs o cantidades específicas, verifica
     * comportamientos y estados esperados que no dependan de un estado previo. */
    @Test
    public void crearOdontologoTest() {
        /* esta línea prepara el objeto simulado odontologoRepository para que, cuando su método save sea invocado con
         * cualquier objeto de tipo Odontologo, retorne el objeto odontologo que hemos creado en nuestro test. Esto nos
         * permite probar el servicio OdontologoService sin depender del comportamiento real del repositorio,
         * lo cual es ideal para pruebas unitarias */
        when(odontologoRepository.save(any(Odontologo.class))).thenReturn(odontologo);
        Odontologo odontologoGuardado = odontologoService.crearOdontologo(odontologo);
        assertNotNull(odontologoGuardado);
        verify(odontologoRepository).save(any(Odontologo.class));
    }

    @Test
    public void buscarOdontologoPorIDNoEncontradoTest() {
        when(odontologoRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> odontologoService.buscarOdontologoPorID(odontologoId));
    }

    @Test
    public void actualizarOdontologoTest() {
        // configura el comportamiento (orden de operaciones en el test) antes de llamar al método que estamos probando
        when(odontologoRepository.findById(anyLong())).thenReturn(Optional.of(odontologo));
        when(odontologoRepository.save(any(Odontologo.class))).thenReturn(odontologo);

        odontologo.setApellido("Hijuela Florian");
        Odontologo resultado = odontologoService.actualizarOdontologo(odontologo);

        assertNotNull(resultado);
        assertEquals("Hijuela Florian", resultado.getApellido());

        verify(odontologoRepository).findById(odontologoId);
        verify(odontologoRepository).save(odontologo);
    }

    @Test
    public void actualizarOdontologoNoEncontradoTest() {
        when(odontologoRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> odontologoService.actualizarOdontologo(odontologo));
    }

    @Test
    public void buscarOdontologoPorMatriculaTest() {
        when(odontologoRepository.findByMatricula(anyString())).thenReturn(Optional.of(odontologo));
        Odontologo odontologoBuscado = odontologoService.buscarOdontologoPorMatricula("00000");
        assertNotNull(odontologoBuscado);
        verify(odontologoRepository).findByMatricula("00000");
    }

    @Test
    public void buscarOdontologoPorMatriculaNoEncontradoTest() {
        when(odontologoRepository.findByMatricula(anyString())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> odontologoService.buscarOdontologoPorMatricula("00000"));
    }

    @Test
    public void eliminarOdontologoTest() {
        when(odontologoRepository.findById(anyLong())).thenReturn(Optional.of(odontologo));
        when(turnoValidationService.existeTurnoParaOdontologo(anyLong())).thenReturn(false);
        doNothing().when(odontologoRepository).deleteById(anyLong());

        odontologoService.eliminarOdontologo(odontologoId);

        verify(odontologoRepository).findById(odontologoId);
        verify(turnoValidationService).existeTurnoParaOdontologo(odontologoId);
        verify(odontologoRepository).deleteById(odontologoId);
    }

    @Test
    public void eliminarOdontologoNoEncontradoTest() {
        when(odontologoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> odontologoService.eliminarOdontologo(odontologoId));
        verify(odontologoRepository).findById(odontologoId);
        verify(turnoValidationService, never()).existeTurnoParaOdontologo(anyLong());
        verify(odontologoRepository, never()).deleteById(anyLong());
    }

    @Test
    public void eliminarOdontologoConTurnosTest() {
        when(odontologoRepository.findById(anyLong())).thenReturn(Optional.of(odontologo));
        when(turnoValidationService.existeTurnoParaOdontologo(anyLong())).thenReturn(true);

        assertThrows(DataIntegrityViolationException.class, () -> odontologoService.eliminarOdontologo(odontologoId));
        verify(odontologoRepository).findById(odontologoId);
        verify(turnoValidationService).existeTurnoParaOdontologo(odontologoId);
        verify(odontologoRepository, never()).deleteById(anyLong());
    }

    @Test
    public void listarTodosLosOdontologosTest() {
        List<Odontologo> odontologos = Arrays.asList(odontologo, new Odontologo("00001", "Petter", "Pérez"));
        when(odontologoRepository.findAll()).thenReturn(odontologos);

        List<Odontologo> resultado = odontologoService.listarTodosLosOdontologos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(odontologoRepository).findAll();
    }
}
