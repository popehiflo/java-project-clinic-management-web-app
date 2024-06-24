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
public class TurnoIdControllerIntegrationTest {

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
    private Long turnoId; // id del turno creado


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

        // Crear el turno y almacenar id
        try {
            turnoId = crearTurnoYObtenerId(turnoDTO);
        } catch (Exception e) {
            // Manejar la excepción si ocurre un error al crear el turno
            fail("Failed al crear el turno en setUp(): " + e.getMessage());
        }
    }

    // Método auxiliar para crear un turno y obtener su ID
    private Long crearTurnoYObtenerId(TurnoDTO turnoDTO) throws Exception {
        String turnoJson = objectMapper.writeValueAsString(turnoDTO);
        MvcResult respuesta = mockMvc.perform(MockMvcRequestBuilders.post("/turnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(turnoJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        return objectMapper.readValue(respuesta.getResponse().getContentAsString(), TurnoDTO.class).getId();
    }

    @Test
    @Order(1)
    void deberiaCrearUnTurno() throws Exception {
        // Esta prueba ya no es necesaria, ya que el turno se crea en setUp()
        assertTrue(turnoId > 0); // verificar que id del turno sea válido
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
                .andExpect(result -> assertInstanceOf(ResourceNotFoundException.class, result.getResolvedException()))
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
                .andExpect(result -> assertInstanceOf(ResourceNotFoundException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals("Odontólogo no encontrado", result.getResolvedException().getMessage()));
    }

    @Test
    @Order(4)
        // Se ejecuta cuarto
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

    @Test
    @Order(5)
        // Se ejecuta quinto
    void deberiaBuscarTurnoPorId() throws Exception {
        // Act
        MvcResult respuesta = mockMvc.perform(MockMvcRequestBuilders.get("/turnos/" + turnoId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Assert
        TurnoDTO turnoEncontrado = objectMapper.readValue(respuesta.getResponse().getContentAsString(), TurnoDTO.class);
        assertEquals(turnoId, turnoEncontrado.getId()); // Verificar con el id almacenado
    }

    @Test
    @Order(6)
        // Se ejecuta sexto
    void deberiaActualizarTurno() throws Exception {
        // Arrange
        turnoDTO.setId(turnoId);
        turnoDTO.setFecha(LocalDate.now().plusDays(1)); // Modifica la fecha
        String turnoJson = objectMapper.writeValueAsString(turnoDTO);

        // Act
        MvcResult respuesta = mockMvc.perform(MockMvcRequestBuilders.put("/turnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(turnoJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Assert
        TurnoDTO turnoActualizado = objectMapper.readValue(respuesta.getResponse().getContentAsString(), TurnoDTO.class);
        assertEquals(turnoDTO.getFecha(), turnoActualizado.getFecha()); // Verifica que la fecha se haya actualizado
    }

    @Test
    @Order(7)
        // Se ejecuta último
    void deberiaEliminarTurno() throws Exception {
        // Act
        mockMvc.perform(MockMvcRequestBuilders.delete("/turnos/" + turnoId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/turnos/" + turnoId))
                .andExpect(MockMvcResultMatchers.status().isNotFound()); // Verifica que el turno ya no exista
    }

}
