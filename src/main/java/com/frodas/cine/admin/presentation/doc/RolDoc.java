package com.frodas.cine.admin.presentation.doc;

import com.frodas.cine.admin.presentation.dto.FiltroNombreDto;
import com.frodas.cine.admin.presentation.dto.RolDto;
import com.frodas.cine.admin.presentation.advice.SuccesResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "RolController", description = "REST API for rol information.")
public interface RolDoc {

    @Operation(
            summary = "Listar Roles",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = RolDto.class)))
            }
    )
    ResponseEntity<SuccesResponse<List<RolDto>>> listAll();

    @Operation(
            summary = "Listar Roles Paginado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = RolDto.class)))
            }
    )
    ResponseEntity<SuccesResponse<Page<RolDto>>> listPageable(Pageable pageable);

    @Operation(
            summary = "Listar Roles Paginado y Filtro Nombre, Descripcion",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = RolDto.class))),
                    @ApiResponse(responseCode = "404", description = "Rol no encontrado", content = @Content)
            }
    )
    ResponseEntity<SuccesResponse<Page<RolDto>>> search(FiltroNombreDto dto, Pageable pageable);

    @Operation(
            summary = "Obtener Detalle de Rol",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = RolDto.class))),
                    @ApiResponse(responseCode = "400", description = "ID proporcionado invalido", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Rol no encontrado", content = @Content)
            }
    )
    ResponseEntity<SuccesResponse<RolDto>> findById(Integer id);

    @Operation(
            summary = "Registrar Rol",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = RolDto.class)))
            }
    )
    ResponseEntity<SuccesResponse<RolDto>> create(RolDto dto);

    @Operation(
            summary = "Actualizar Rol",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema(implementation = RolDto.class)))
            }
    )
    ResponseEntity<SuccesResponse<RolDto>> update(RolDto dto);

    @Operation(
            summary = "Eliminar Rol",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(schema = @Schema()))
            }
    )
    ResponseEntity<SuccesResponse<Void>> delete(Integer id);

}
