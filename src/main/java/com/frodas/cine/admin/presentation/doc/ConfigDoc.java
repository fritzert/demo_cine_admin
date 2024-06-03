package com.frodas.cine.admin.presentation.doc;

import com.frodas.cine.admin.presentation.advice.SuccesResponse;
import com.frodas.cine.admin.presentation.dto.ConfigDto;
import com.frodas.cine.admin.presentation.dto.FiltroNombreDto;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

@Tag(name = "ConfigController", description = "REST API for configuration information.")
public interface ConfigDoc {

    @Operation(
            summary = "Listar Configuraciones",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = ConfigDto.class))
                    )
            }
    )
    ResponseEntity<SuccesResponse<List<ConfigDto>>> listAll(HttpHeaders headers);

    @Operation(
            summary = "Listar Configuraciones Paginado y Filtro parametro, valor",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = ConfigDto.class))),
                    @ApiResponse(responseCode = "404", description = "Configuracion no encontrado", content = @Content)
            }
    )
    ResponseEntity<SuccesResponse<Page<ConfigDto>>> search(FiltroNombreDto dto, Pageable pageable);

    @Operation(
            summary = "Obtener Detalle de Configuracion",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = ConfigDto.class))),
                    @ApiResponse(responseCode = "400", description = "ID proporcionado invalido", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Configuracion no encontrada", content = @Content)
            }
    )
    ResponseEntity<SuccesResponse<ConfigDto>> findById(UUID id);

    @Operation(
            summary = "Obtener Detalle Por Parametro",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = ConfigDto.class))),
                    @ApiResponse(responseCode = "400", description = "Parametro proporcionado invalido", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Configuracion no encontrada", content = @Content)
            }
    )
    @Hidden
    ResponseEntity<SuccesResponse<ConfigDto>> findByParam(String param);

    @Operation(
            summary = "Registrar Configuracion",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = ConfigDto.class)))
            }
    )
    ResponseEntity<SuccesResponse<ConfigDto>> create(ConfigDto dto);

    @Operation(
            summary = "Actualizar Configuracion",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = ConfigDto.class)))
            }
    )
    ResponseEntity<SuccesResponse<ConfigDto>> update(ConfigDto dto);

    @Operation(
            summary = "Eliminar Configuracion",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema())),
                    @ApiResponse(responseCode = "400", description = "ID proporcionado invalido", content = @Content),
            }
    )
    ResponseEntity<SuccesResponse<Void>> delete(UUID id);

}
