package com.frodas.cine.admin.service.interfaces;

import com.frodas.cine.admin.presentation.dto.ClienteReqDto;
import com.frodas.cine.admin.presentation.dto.ClienteResDto;
import com.frodas.cine.admin.presentation.dto.ImagenDto;

import java.util.UUID;

public interface PerfilService {

    ClienteResDto findByEmail(String email);

    ClienteResDto updateInfoCliente(ClienteReqDto dto);

    ClienteResDto updateInfoImg(UUID idCliente, ImagenDto dto);

}
