package com.frodas.cine.admin.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FiltroConsultaDto {

    @NotBlank(message = "No debe ser nulo, vacio o espacios en blanco")
    private String dni;

    @NotBlank(message = "No debe ser nulo, vacio o espacios en blanco")
    private String nombreCompleto;

    private LocalDateTime fechaConsulta;

}
