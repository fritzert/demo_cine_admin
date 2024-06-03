package com.frodas.cine.admin.service.implementation;

import com.frodas.cine.admin.presentation.dto.ClienteReqDto;
import com.frodas.cine.admin.presentation.dto.ClienteResDto;
import com.frodas.cine.admin.presentation.dto.ImagenDto;
import com.frodas.cine.admin.persistence.entity.Cliente;
import com.frodas.cine.admin.service.exception.BusinessException;
import com.frodas.cine.admin.persistence.repository.ClienteRepository;
import com.frodas.cine.admin.service.interfaces.PerfilService;
import com.frodas.cine.admin.util.mapper.EntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static com.frodas.cine.admin.util.constants.ConstantsMessage.MSG_ID_NOT_FOUND;
import static com.frodas.cine.admin.util.constants.ConstantsMessage.MSG_VALUE_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PerfilServiceImpl implements PerfilService {

    private final ClienteRepository clienteRepo;
    private final EntityMapper mapper;

    @Override
    @Transactional
    public ClienteResDto updateInfoCliente(ClienteReqDto dto) {
        Cliente entity = clienteRepo.findById(dto.getIdCliente())
                .orElseThrow(() -> new BusinessException(String.format(MSG_ID_NOT_FOUND, dto.getIdCliente())));
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setDni(dto.getDni());
        entity.setFechaNac(dto.getFechaNac());
        entity.getUsuario().setFullName(dto.getFirstName() + " " + dto.getLastName());
        entity.getUsuario().setEmail(dto.getEmail());
        Cliente newEntity = clienteRepo.save(entity);
        return mapper.entityToApi(newEntity);
    }

    @Override
    @Transactional
    public ClienteResDto updateInfoImg(UUID idCliente, ImagenDto dto) {
        Cliente entity = clienteRepo.findById(idCliente)
                .orElseThrow(() -> new BusinessException(String.format(MSG_ID_NOT_FOUND, idCliente)));
        entity.getUsuario().setImagenName(dto.getImagenName());
        entity.getUsuario().setImagenUrl(dto.getImagenUrl());
        entity.getUsuario().setImagenId(dto.getImagenId());
        Cliente newEntity = clienteRepo.save(entity);
        return mapper.entityToApi(newEntity);
    }

    @Override
    public ClienteResDto findByEmail(String email) {
        //String emailValue = Optional.ofNullable(email).filter(val -> !val.isBlank())
        //        .orElseThrow(() -> new BusinessException(String.format(MSG_VALUE_NOT_FOUND, email)));
        Optional<String> opEmail = Optional.ofNullable(email);
        String emailValue = opEmail.filter(val -> !val.isBlank())
                .orElseThrow(() -> new BusinessException(String.format(MSG_VALUE_NOT_FOUND, email)));
        Cliente entity = clienteRepo.findByEmail(emailValue);
        return mapper.entityToApi(entity);
    }

}
