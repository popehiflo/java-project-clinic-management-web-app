package io.github.popehiflo.clinica.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.popehiflo.clinica.dto.TurnoDTO;
import io.github.popehiflo.clinica.entity.Domicilio;
import io.github.popehiflo.clinica.entity.Odontologo;
import io.github.popehiflo.clinica.entity.Paciente;
import io.github.popehiflo.clinica.exception.ResourceNotFoundException;
import io.github.popehiflo.clinica.service.OdontologoService;
import io.github.popehiflo.clinica.service.PacienteService;
import io.github.popehiflo.clinica.service.TurnoService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Para ejecutar pruebas en orden
public class TurnoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private TurnoService turnoService;

    @Autowired
    private ObjectMapper objectMapper; // Para serializar/deserializar objetos JSON

    private TurnoDTO turnoDTO; // objeto prueba compartido
    private Long pacienteId; // id del paciente creado
    private Long odontologoId; // id del odontólogo creado

    @BeforeEach
    public void setUp() {
        // Crear un paciente
        Paciente paciente = new Paciente("Pool", "Hijuela", "12345678", LocalDate.now(), new Domicilio("Nueva", 123, "Cusco", "Cusco"), "popehiflo@gmail.com");
        Paciente pacienteCreado = pacienteService.crearPaciente(paciente);
        pacienteId = pacienteCreado.getId();

        // Crear un odontólogo
        Odontologo odontologo = new Odontologo("OD1234", "Petter", "Florian");
        Odontologo odontologoCreado = odontologoService.crearOdontologo(odontologo);
        odontologoId = odontologoCreado.getId();

        // Crear el DTO del turno con los ids creados
        turnoDTO = new TurnoDTO();
        turnoDTO.setPacienteId(pacienteId);
        turnoDTO.setOdontologoId(odontologoId);
        turnoDTO.setFecha(LocalDate.now());
    }

    @Test
    @Order(1)
    void deberiaCrearUnTurno() throws Exception {
        // Arrange
        String turnoJson = objectMapper.writeValueAsString(turnoDTO);

        // Act
        MvcResult respuesta = mockMvc.perform(MockMvcRequestBuilders.post("/turnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(turnoJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Assert
        TurnoDTO turnoCreado = objectMapper.readValue(respuesta.getResponse().getContentAsString(), TurnoDTO.class);
        assertNotNull(turnoCreado.getId());
        assertEquals(turnoDTO.getPacienteId(), turnoCreado.getPacienteId());
        assertEquals(turnoDTO.getOdontologoId(), turnoCreado.getOdontologoId());
        assertEquals(turnoDTO.getFecha(), turnoCreado.getFecha());
    }

    @Test
    @Order(2)
    void deberiaLanzarExcepcionAlCrearTurnoConPacienteInexistente() throws Exception {
        // Arrange
        turnoDTO.setPacienteId(999L); // ID de paciente inexistente
        String turnoJson = objectMapper.writeValueAsString(turnoDTO);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/turnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(turnoJson))
                .andExpect(MockMvcResultMatchers.status().isNotFound()) // Esperamos un 404 Not Found
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("Paciente no encontrado", result.getResolvedException().getMessage()));
    }

    @Test
    @Order(3)
    void deberiaLanzarExcepcionAlCrearTurnoConOdontologoInexistente() throws Exception {
        // Arrange
        turnoDTO.setOdontologoId(999L); // ID de odontólogo inexistente
        String turnoJson = objectMapper.writeValueAsString(turnoDTO);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/turnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(turnoJson))
                .andExpect(MockMvcResultMatchers.status().isNotFound()) // Esperamos un 404 Not Found
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("Odontólogo no encontrado", result.getResolvedException().getMessage()));
    }

    @Test
    @Order(4) // Se ejecuta cuarto
    void deberiaListarTodosLosTurnos() throws Exception {
        // Act
        MvcResult respuesta = mockMvc.perform(MockMvcRequestBuilders.get("/turnos")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Assert
        List<TurnoDTO> turnos = Arrays.asList(objectMapper.readValue(respuesta.getResponse().getContentAsString(), TurnoDTO[].class));
        assertFalse(turnos.isEmpty()); // Verifica que la lista no esté vacía
    }

}
