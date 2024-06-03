package com.frodas.cine.admin.presentation.dto;

import com.frodas.cine.admin.persistence.entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResDto implements Serializable {

    private UUID idCliente;

    private String firstName;

    private String lastName;

    private LocalDate fechaNac;

//    @MaskData
    //enmascarar datos PII (Personal Identifiable Information)
    private String dni;

    private UsuarioDto usuario;

    public ClienteResDto(Cliente entity) {
        this.idCliente = entity.getIdCliente();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.fechaNac = entity.getFechaNac();
        this.dni = entity.getDni();
        this.usuario = new UsuarioDto(entity.getUsuario());
    }

}
