package com.frodas.cine.admin.presentation.controller;

import com.frodas.cine.admin.presentation.advice.SuccesResponse;
import com.frodas.cine.admin.presentation.advice.SuccesResponseHandler;
import com.frodas.cine.admin.presentation.doc.ConfigDoc;
import com.frodas.cine.admin.presentation.dto.ConfigDto;
import com.frodas.cine.admin.presentation.dto.FiltroNombreDto;
import com.frodas.cine.admin.service.interfaces.ConfigService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.UUID;

import static com.frodas.cine.admin.util.constants.ConstantsPath.PATH_CONFIGURACIONES;

@RestController
@RequestMapping(PATH_CONFIGURACIONES)
@RequiredArgsConstructor
@Slf4j
public class ConfigController implements ConfigDoc {

    private final ConfigService configService;

    @Override
    @GetMapping
    public ResponseEntity<SuccesResponse<List<ConfigDto>>> listAll(@RequestHeader HttpHeaders headers) {
        InetSocketAddress host = headers.getHost();
        String url = "http://" + host.getHostName() + ":" + host.getPort();
        log.info(String.format("Base URL = %s", url));
        List<ConfigDto> lista = configService.listar();
        return SuccesResponseHandler.SUCCESS(lista);
    }

    @Override
    @PostMapping(value = "/buscar")
    public ResponseEntity<SuccesResponse<Page<ConfigDto>>> search(@RequestBody FiltroNombreDto dto,
                                                                  @PageableDefault(page = 0, size = 10, sort = "nombre", direction = Sort.Direction.ASC)
                                                                  Pageable pageable) {
        Page<ConfigDto> list;
        if (dto.getNombre().isBlank())
            list = configService.listarPageable(pageable);
        else
            list = configService.listarPageableNombreValor(dto.getNombre(), pageable);
        return SuccesResponseHandler.SUCCESS(list);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<SuccesResponse<ConfigDto>> findById(@PathVariable("id") UUID id) {
        ConfigDto dto = configService.buscarPorId(id);
        return SuccesResponseHandler.SUCCESS(dto);
    }

    @Override
    @GetMapping(value = "/buscar/{param}")
    public ResponseEntity<SuccesResponse<ConfigDto>> findByParam(@PathVariable("param") String param) {
        ConfigDto dto = configService.leerParametro(param);
        return SuccesResponseHandler.SUCCESS(dto);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    //@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')") // ACCESO POR ROL
    //@PreAuthorize("@restAuthService.hasAccess('listar')") // INDICANDO QUE ROLES TIENE ACCESO A LISTAR
    // @RequestBody conversion Json a Object
    // @Valid para leer las validaciones del modelo
    public ResponseEntity<SuccesResponse<ConfigDto>> create(@Valid @RequestBody ConfigDto dto) { //, BindingResult bindingResult
        ConfigDto newDto = configService.registrar(dto);
        return SuccesResponseHandler.SUCCESS(201, newDto);
    }

    @Override
    @PutMapping
    public ResponseEntity<SuccesResponse<ConfigDto>> update(@RequestBody ConfigDto dto) {
        ConfigDto newDto = configService.actualizar(dto);
        return SuccesResponseHandler.SUCCESS(newDto);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccesResponse<Void>> delete(@PathVariable("id") UUID id) {
        configService.eliminar(id);
        return SuccesResponseHandler.SUCCESS();
    }

}
