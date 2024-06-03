package com.frodas.cine.admin.service.interfaces;

import com.frodas.cine.admin.presentation.dto.ConfigDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConfigService extends CRUD<ConfigDto> {

    ConfigDto leerParametro(String param);

    Page<ConfigDto> listarPageable(Pageable pageable);

    Page<ConfigDto> listarPageableNombreValor(String nombre, Pageable pageable);

}
