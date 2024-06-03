package com.frodas.cine.admin.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class ClienteReqDto implements Serializable {

    private UUID idCliente;

    @NotBlank(message = "[nombre] No debe ser nulo, vacio o espacios en blanco")
    @Size(min = 3, max = 80, message = "[firstname] Debe tener entre 3 y 80 caracteres")
    private String firstName;

    @NotBlank(message = "[nombre] No debe ser nulo, vacio o espacios en blanco")
    @Size(min = 3, max = 80, message = "[lastname] Debe tener entre 3 y 80 caracteres")
    private String lastName;

    @Email
    private String email;

//    @NotBlank(message = "[password] No debe ser nulo, vacio o espacios en blanco")
//    @Size(min = 3, message = "[password] Minimo 3 caracteres")
//    private String password;

    //    @NotNull
    private LocalDate fechaNac;

    //    @NotNull
    private String dni;

}
