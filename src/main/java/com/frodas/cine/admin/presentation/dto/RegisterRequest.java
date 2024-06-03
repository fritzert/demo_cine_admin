package com.frodas.cine.admin.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "No debe ser nulo, vacio o espacios en blanco")
    @Size(min = 3, max = 80, message = "[firstname] Debe tener entre 3 y 80 caracteres")
    private String firstName;

    @NotBlank(message = "No debe ser nulo, vacio o espacios en blanco")
    @Size(min = 3, max = 80, message = "[lastname] Debe tener entre 3 y 80 caracteres")
    private String lastName;

    @Email
    private String email;

    @NotBlank(message = "No debe ser nulo, vacio o espacios en blanco")
    @Size(min = 3, message = "[password] Minimo 3 caracteres")
    private String password;

}
