package com.frodas.cine.admin.presentation.controller;

import com.frodas.cine.admin.presentation.doc.RolDoc;
import com.frodas.cine.admin.presentation.dto.FiltroNombreDto;
import com.frodas.cine.admin.presentation.dto.RolDto;
import com.frodas.cine.admin.service.interfaces.RolService;
import com.frodas.cine.admin.presentation.advice.SuccesResponse;
import com.frodas.cine.admin.presentation.advice.SuccesResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.frodas.cine.admin.util.constants.ConstantsPath.PATH_ROLES;

@RestController
@RequestMapping(PATH_ROLES)
@RequiredArgsConstructor
public class RolController implements RolDoc {

    private final RolService rolService;

    @Override
    @GetMapping
    public ResponseEntity<SuccesResponse<List<RolDto>>> listAll() {
        List<RolDto> list = rolService.listar();
        return SuccesResponseHandler.SUCCESS(list);
    }

    @Override
    @GetMapping(value = "/pageable")
    public ResponseEntity<SuccesResponse<Page<RolDto>>> listPageable(Pageable pageable) {
        Page<RolDto> list = rolService.listarPageable(pageable);
        return SuccesResponseHandler.SUCCESS(list);
    }

    @Override
    @PostMapping(value = "/buscar")
    public ResponseEntity<SuccesResponse<Page<RolDto>>> search(@RequestBody FiltroNombreDto dto, Pageable pageable) {
        Page<RolDto> list;
        if (dto.getNombre().isBlank())
            list = rolService.listarPageable(pageable);
        else
            list = rolService.listarPageableNombreDetalle(dto.getNombre(), pageable);
        return SuccesResponseHandler.SUCCESS(list);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<SuccesResponse<RolDto>> findById(@PathVariable("id") Integer id) {
        RolDto dto = rolService.buscarPorId(id);
        return SuccesResponseHandler.SUCCESS(dto);
    }

    @Override
    @PostMapping
    public ResponseEntity<SuccesResponse<RolDto>> create(@RequestBody RolDto dto) {
        RolDto newDto = rolService.registrar(dto);
        return SuccesResponseHandler.SUCCESS(201, newDto);
    }

    @Override
    @PutMapping
    public ResponseEntity<SuccesResponse<RolDto>> update(@RequestBody RolDto dto) {
        RolDto newDto = rolService.actualizar(dto);
        return SuccesResponseHandler.SUCCESS(newDto);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccesResponse<Void>> delete(@PathVariable("id") Integer id) {
        rolService.eliminar(id);
        return SuccesResponseHandler.SUCCESS();
    }

}
