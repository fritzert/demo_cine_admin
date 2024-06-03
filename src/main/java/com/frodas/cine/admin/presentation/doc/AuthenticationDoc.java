package com.frodas.cine.admin.presentation.doc;

import com.frodas.cine.admin.presentation.dto.AuthenticationRequest;
import com.frodas.cine.admin.presentation.dto.AuthenticationResponse;
import com.frodas.cine.admin.presentation.dto.RegisterRequest;
import com.frodas.cine.admin.presentation.advice.SuccesResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

//@SecurityRequirement(name = "bearerAuth")
@Tag(name = "AuthenticationController", description = "REST API for auth information.")
public interface AuthenticationDoc {

    @Operation(summary = "Registrar Usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(responseCode = "201", description = "Registro existente", content = @Content)
    })
    ResponseEntity<SuccesResponse<AuthenticationResponse>> register(RegisterRequest request);

    @Operation(summary = "Iniciar Sesion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
    })
    ResponseEntity<SuccesResponse<AuthenticationResponse>> login(AuthenticationRequest request);

}
