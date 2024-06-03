package com.frodas.cine.admin.presentation.dto;

import com.frodas.cine.admin.persistence.entity.Rol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RolDto implements Serializable {

    private Integer idRol;

    @NotBlank(message = "[nombre] No debe ser nulo, vacio o espacios en blanco")
    @Size(max = 30, message = "Máximo 30 caracteres")
    private String nombre;

    @NotBlank(message = "[nombre] No debe ser nulo, vacio o espacios en blanco")
    @Size(max = 255, message = "Máximo 255 caracteres")
    private String descripcion;

    public RolDto(Rol entity) {
        this.idRol = entity.getIdRol();
        this.nombre = entity.getNombre();
        this.descripcion = entity.getDescripcion();
    }

}
