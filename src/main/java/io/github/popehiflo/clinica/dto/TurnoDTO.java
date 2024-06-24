package io.github.popehiflo.clinica.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TurnoDTO {

    private Long id;
    private Long pacienteId;
    private Long odontologoId;
    private LocalDate fecha;

}
