package com.frodas.cine.admin.service.implementation;

import com.frodas.cine.admin.config.redis.RedisHandler;
import com.frodas.cine.admin.persistence.entity.Rol;
import com.frodas.cine.admin.persistence.repository.RolRepository;
import com.frodas.cine.admin.presentation.dto.RolDto;
import com.frodas.cine.admin.service.exception.BusinessException;
import com.frodas.cine.admin.service.interfaces.RolService;
import com.frodas.cine.admin.util.mapper.EntityMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.frodas.cine.admin.util.constants.ConstantsMessage.MSG_ID_NOT_FOUND;
import static com.frodas.cine.admin.util.constants.ConstantsRedisKeys.REDIS_ROL_KEY;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;
    private final EntityMapper mapper;
    private final RedisTemplate<String, RolDto> redisRolTemplate;
    private HashOperations<String, String, RolDto> hashOperations;

    @PostConstruct
    private void init() {
        hashOperations = redisRolTemplate.opsForHash();
    }

    @Override
    public Page<RolDto> listarPageable(Pageable pageable) {
        return rolRepository.findAll(pageable).map(RolDto::new);
    }

    @Override
    public Page<RolDto> listarPageableNombreDetalle(String nombre, Pageable pageable) {
        return rolRepository.findAllByNombreContainsIgnoreCaseOrDescripcionContainsIgnoreCase(nombre, nombre, pageable).map(RolDto::new);
    }

    @Override
    @Transactional
    public RolDto registrar(RolDto dto) {
        Rol entity = mapper.apiToEntity(dto);
        Rol newEntity = rolRepository.save(entity);
        deleteDataCache();
        return mapper.entityToApi(newEntity);
    }

    @Override
    @Transactional
    public RolDto actualizar(RolDto dto) {
        Rol entity = mapper.apiToEntity(dto);
        entity.setIdRol(dto.getIdRol());
        Rol newEntity = rolRepository.save(entity);
        deleteDataCache();
        return mapper.entityToApi(newEntity);
    }

    @Override
    public List<RolDto> listar() {
        RedisHandler<RolDto> redisHandler = RedisHandler.<RolDto>builder().key(REDIS_ROL_KEY).hashOperations(hashOperations).build();
        List<RolDto> data = redisHandler.findAll();
        if (data.isEmpty()) {
            List<Rol> entityList = rolRepository.findAll();
            data = mapper.entityListToApiListRol(entityList);
            Map<String, RolDto> dataCache = data.stream().collect(Collectors.toMap(x -> x.getIdRol().toString(), x -> x));
            redisHandler.saveAll(dataCache);
        }
        return data;
    }

    @Override
    public RolDto buscarPorId(Integer id) {
        Rol entity = rolRepository.findById(id)
                .orElseThrow(() -> new BusinessException(String.format(MSG_ID_NOT_FOUND, id)));
        return mapper.entityToApi(entity);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        Rol entity = rolRepository.findById(id)
                .orElseThrow(() -> new BusinessException(String.format(MSG_ID_NOT_FOUND, id)));
        rolRepository.deleteById(entity.getIdRol());
        deleteDataCache();

    }

    @Override
    @Transactional
    public void updateEliminar(Integer id) {
        Rol entity = rolRepository.findById(id)
                .orElseThrow(() -> new BusinessException(String.format(MSG_ID_NOT_FOUND, id)));
        rolRepository.save(entity);
        deleteDataCache();
    }

    private void deleteDataCache() {
        RedisHandler<RolDto> redisHandler = RedisHandler.<RolDto>builder().key(REDIS_ROL_KEY).hashOperations(hashOperations).build();
        List<RolDto> data = redisHandler.findAll();
        for (RolDto datum : data) {
            redisHandler.delete(datum.getIdRol().toString());
        }
    }

}
