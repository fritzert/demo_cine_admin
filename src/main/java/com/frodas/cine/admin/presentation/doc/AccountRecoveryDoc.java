package com.frodas.cine.admin.presentation.doc;

import com.frodas.cine.admin.presentation.advice.SuccesResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "AccountRecoveryController", description = "REST API for account recovery information.")
public interface AccountRecoveryDoc {

    @Operation(summary = "Enviar email a usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "400", description = "Error en la operación", content = @Content)
    })
    ResponseEntity<SuccesResponse<Void>> enviarCorreo(String email);

    @Operation(summary = "Verificar validez del token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "400", description = "Error en la operación", content = @Content)
    })
    ResponseEntity<SuccesResponse<Boolean>> verificarToken(String token);

    @Operation(summary = "Restablecer contraseña")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "400", description = "Error en la operación", content = @Content)
    })
    ResponseEntity<SuccesResponse<Void>> restablecerClave(String token, String password);

}
