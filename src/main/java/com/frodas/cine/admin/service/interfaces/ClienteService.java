package com.frodas.cine.admin.service.interfaces;

import com.frodas.cine.admin.presentation.dto.ClienteReqDto;
import com.frodas.cine.admin.presentation.dto.ClienteResDto;
import com.frodas.cine.admin.presentation.dto.ImagenDto;
import com.frodas.cine.admin.presentation.dto.UsuarioRolDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ClienteService {

    ClienteResDto registrar(ClienteReqDto obj, ImagenDto imagenDto);

    ClienteResDto actualizar(ClienteReqDto obj, ImagenDto imagenDto);

    List<ClienteResDto> listar();

    Page<ClienteResDto> listarPageable(Pageable pageable);

    Page<ClienteResDto> listarPageableNombre(String nombre, Pageable pageable);

    ClienteResDto buscarPorId(UUID id);

    ClienteResDto buscarPorDni(String id);

    void eliminar(UUID id);

    void updateEliminar(UUID id);

    List<UsuarioRolDto> listUsuarioRolByCliente(UUID idCliente);

    void procesarUsuarioRol(UUID idCliente, List<UsuarioRolDto> dto);

}
