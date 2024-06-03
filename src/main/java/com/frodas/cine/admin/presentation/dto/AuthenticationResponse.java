package com.frodas.cine.admin.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {

    private String fullName;

    private String email;

    private String imagenUrl;

//    @JsonProperty("access_token")
    private String accessToken;

//    @JsonProperty("refresh_token")
    private String refreshToken;

}
