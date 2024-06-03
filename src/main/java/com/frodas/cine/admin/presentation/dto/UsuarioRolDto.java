package com.frodas.cine.admin.presentation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRolDto implements Serializable {

    private UUID idUsuario;

    @NotNull(message = "[idRol] No debe ser nulo")
    private Integer idRol;

    private String nombre;

    private String descripcion;

}
