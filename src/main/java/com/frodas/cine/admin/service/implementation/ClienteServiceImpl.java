package com.frodas.cine.admin.service.implementation;

import com.frodas.cine.admin.persistence.entity.Cliente;
import com.frodas.cine.admin.persistence.entity.Usuario;
import com.frodas.cine.admin.persistence.repository.ClienteRepository;
import com.frodas.cine.admin.persistence.repository.UsuarioRepository;
import com.frodas.cine.admin.presentation.dto.ClienteReqDto;
import com.frodas.cine.admin.presentation.dto.ClienteResDto;
import com.frodas.cine.admin.presentation.dto.ImagenDto;
import com.frodas.cine.admin.presentation.dto.UsuarioRolDto;
import com.frodas.cine.admin.service.exception.BusinessException;
import com.frodas.cine.admin.util.mapper.EntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.frodas.cine.admin.util.constants.ConstantsMessage.MSG_ID_NOT_FOUND;
import static com.frodas.cine.admin.util.constants.ConstantsMessage.MSG_VALUE_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ClienteServiceImpl implements com.frodas.cine.admin.service.interfaces.ClienteService {

    private final ClienteRepository clienteRepo;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityMapper mapper;

    @Value("${app.default.rol}")
    private Integer DEFAULT_ROL;

    @Override
    @Transactional
    public ClienteResDto registrar(ClienteReqDto dto, ImagenDto imagenDto) {
        var user = Usuario.builder()
                .fullName(dto.getFirstName() + " " + dto.getLastName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getDni()))
                .estado(Boolean.TRUE)
                .imagenName(imagenDto.getImagenName())
                .imagenUrl(imagenDto.getImagenUrl())
                .imagenId(imagenDto.getImagenId())
                .build();
        var cliente = Cliente.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .fechaNac(dto.getFechaNac())
                .dni(dto.getDni())
                .build();
        user.setCliente(cliente);
        cliente.setUsuario(user);

        var savedUser = usuarioRepository.save(user);
        usuarioRepository.registrarRolPorDefecto(savedUser.getIdUsuario(), DEFAULT_ROL);
        return mapper.entityToApi(savedUser.getCliente());
    }

    @Override
    @Transactional
    public ClienteResDto actualizar(ClienteReqDto dto, ImagenDto imagenDto) {
        Cliente entity = clienteRepo.findById(dto.getIdCliente())
                .orElseThrow(() -> new BusinessException(String.format(MSG_ID_NOT_FOUND, dto.getIdCliente())));
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setDni(dto.getDni());
        entity.setFechaNac(dto.getFechaNac());
        entity.getUsuario().setFullName(dto.getFirstName() + " " + dto.getLastName());
        entity.getUsuario().setEmail(dto.getEmail());
        if (imagenDto != null) {
            entity.getUsuario().setImagenName(imagenDto.getImagenName());
            entity.getUsuario().setImagenUrl(imagenDto.getImagenUrl());
            entity.getUsuario().setImagenId(imagenDto.getImagenId());
        }
        Cliente newEntity = clienteRepo.save(entity);
        return mapper.entityToApi(newEntity);
    }

    @Override
    public List<ClienteResDto> listar() {
        List<Cliente> entityList = clienteRepo.findAll();
        return mapper.entityListToApiListCliente(entityList);
    }

    @Override
    public Page<ClienteResDto> listarPageable(Pageable pageable) {
        return clienteRepo.findAll(pageable).map(ClienteResDto::new);
    }

    @Override
    public Page<ClienteResDto> listarPageableNombre(String nombre, Pageable pageable) {
        return clienteRepo.buscarDatosNombresEmail(nombre, pageable).map(ClienteResDto::new);
    }

    @Override
    public ClienteResDto buscarPorId(UUID id) {
        Cliente entity = clienteRepo.findById(id)
                .orElseThrow(() -> new BusinessException(String.format(MSG_ID_NOT_FOUND, id)));
        return mapper.entityToApi(entity);
    }

    @Override
    public ClienteResDto buscarPorDni(String dni) {
        Cliente entity = clienteRepo.findByDni(dni)
                .orElseThrow(() -> new BusinessException(String.format(MSG_VALUE_NOT_FOUND, dni)));
        return mapper.entityToApi(entity);
    }

    @Override
    @Transactional
    public void eliminar(UUID id) {
        Cliente entity = clienteRepo.findById(id)
                .orElseThrow(() -> new BusinessException(String.format(MSG_ID_NOT_FOUND, id)));
        clienteRepo.deleteById(entity.getIdCliente());
    }

    @Override
    @Transactional
    public void updateEliminar(UUID id) {
        Cliente entity = clienteRepo.findById(id)
                .orElseThrow(() -> new BusinessException(String.format(MSG_ID_NOT_FOUND, id)));
        clienteRepo.save(entity);
    }

    @Override
    public List<UsuarioRolDto> listUsuarioRolByCliente(UUID idMenu) {
        Cliente entity = clienteRepo.findById(idMenu)
                .orElseThrow(() -> new BusinessException(String.format(MSG_ID_NOT_FOUND, idMenu)));
        List<UsuarioRolDto> usuarioRoles = new ArrayList<>();
        clienteRepo.listarUsuarioRolByMenu(entity.getIdCliente()).forEach(x -> {
            UsuarioRolDto m = new UsuarioRolDto();
            m.setIdRol((Integer) x[0]);
            m.setNombre(String.valueOf(x[1]));
            m.setDescripcion(String.valueOf(x[2]));
            m.setIdUsuario(UUID.fromString(String.valueOf(x[3])));
            usuarioRoles.add(m);
        });
        return usuarioRoles;
    }

    @Override
    @Transactional
    public void procesarUsuarioRol(UUID idCliente, List<UsuarioRolDto> dto) {
        clienteRepo.deleteUsuarioRol(idCliente);
        dto.forEach(x -> clienteRepo.registrarUsuarioRol(idCliente, x.getIdRol()));
    }

}
