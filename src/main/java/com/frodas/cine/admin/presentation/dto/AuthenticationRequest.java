package com.frodas.cine.admin.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

    @Email
    private String email;

    @Size(min = 3, message = "[password] Minimo 3 caracteres")
    private String password;

}
