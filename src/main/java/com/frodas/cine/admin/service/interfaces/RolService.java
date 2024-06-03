package com.frodas.cine.admin.service.interfaces;

import com.frodas.cine.admin.presentation.dto.RolDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RolService {

    Page<RolDto> listarPageable(Pageable pageable);

    Page<RolDto> listarPageableNombreDetalle(String nombre, Pageable pageable);

    RolDto registrar(RolDto dto);

    RolDto actualizar(RolDto dto);

    List<RolDto> listar();

    RolDto buscarPorId(Integer id);

    void eliminar(Integer id);

    void updateEliminar(Integer id);

}
