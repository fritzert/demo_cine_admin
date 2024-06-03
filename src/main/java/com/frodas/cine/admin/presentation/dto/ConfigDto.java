package com.frodas.cine.admin.presentation.dto;

import com.frodas.cine.admin.persistence.entity.Config;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class ConfigDto implements Serializable {

    // CAMBIA DE NOMBRE AL PARAMETRO
    //@JsonProperty("id_config")
    private UUID idConfig;

    @NotBlank(message = "[parametro] No debe ser nulo, vacio o espacios en blanco")
    @Size(max = 5, message = "Máximo 5 caracteres")
    private String parametro;

    @NotBlank(message = "[valor] No debe ser nulo, vacio o espacios en blanco")
    @Size(max = 25, message = "Máximo 25 caracteres")
    private String valor;

    // PARA NO VISUALIZAR EL PARAMETRO
    //@JsonIgnore
    @NotNull
    private Boolean estado;

    public ConfigDto(Config entity) {
        this.idConfig = entity.getIdConfig();
        this.parametro = entity.getParametro();
        this.valor = entity.getValor();
    }

}
