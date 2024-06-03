package com.frodas.cine.admin.presentation.doc;

import com.frodas.cine.admin.presentation.advice.SuccesResponse;
import com.frodas.cine.admin.presentation.dto.ClienteReqDto;
import com.frodas.cine.admin.presentation.dto.ClienteResDto;
import com.frodas.cine.admin.presentation.dto.FiltroNombreDto;
import com.frodas.cine.admin.presentation.dto.UsuarioRolDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Tag(name = "ClienteController", description = "REST API for cliente information.")
public interface ClienteDoc {

    @Operation(
            summary = "Listar Clientes",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = ClienteResDto.class))),
                    @ApiResponse(responseCode = "204", description = "No se encontraron datos", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Error en la operación", content = @Content)
            }
    )
    ResponseEntity<SuccesResponse<List<ClienteResDto>>> listAll();

    @Operation(
            summary = "Listar Clientes Paginado y Filtro Nombre, etc",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = ClienteResDto.class))),
                    @ApiResponse(responseCode = "204", description = "No se encontraron datos", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Error en la operacióņ", content = @Content)
            }
    )
    ResponseEntity<SuccesResponse<Page<ClienteResDto>>> search(FiltroNombreDto dto, Pageable pageable);

    @Operation(
            summary = "Obtener Detalle de Cliente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = ClienteResDto.class))),
                    @ApiResponse(responseCode = "400", description = "ID proporcionado inválido", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Dato no encontrado", content = @Content)
            }
    )
    ResponseEntity<SuccesResponse<ClienteResDto>> findById(UUID id);

    @Operation(
            summary = "Registrar Cliente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = ClienteResDto.class))),
                    @ApiResponse(responseCode = "400", description = "Error en la operacióņ", content = @Content),
                    @ApiResponse(responseCode = "409", description = "Dato existentȩ", content = @Content)
            }
    )
    ResponseEntity<SuccesResponse<ClienteResDto>> create(ClienteReqDto dto, MultipartFile file);

    @Operation(
            summary = "Actualizar Cliente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = ClienteResDto.class))),
                    @ApiResponse(responseCode = "400", description = "Error en la operacióņ", content = @Content)
            }
    )
    ResponseEntity<SuccesResponse<ClienteResDto>> update(ClienteReqDto dto, MultipartFile file);

    @Operation(
            summary = "Actualizar Estado Cliente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema())),
                    @ApiResponse(responseCode = "400", description = "ID proporcionado inválido", content = @Content),
            }
    )
    ResponseEntity<SuccesResponse<Void>> updateEstado(UUID id);

    @Operation(
            summary = "Listar Usuario-Rol por idCliente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = UsuarioRolDto.class))),
                    @ApiResponse(responseCode = "400", description = "ID proporcionado inválido", content = @Content),
            }
    )
    ResponseEntity<SuccesResponse<List<UsuarioRolDto>>> listUsuarioRolByMenu(UUID idCliente);

    @Operation(
            summary = "Crear Usuario-Rol",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = UsuarioRolDto.class))),
                    @ApiResponse(responseCode = "400", description = "ID proporcionado inválido", content = @Content),
            }
    )
    ResponseEntity<SuccesResponse<Void>> createUsuarioRol(UUID id, List<UsuarioRolDto> dto);

}
