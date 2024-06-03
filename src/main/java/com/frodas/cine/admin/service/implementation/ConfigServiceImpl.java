package com.frodas.cine.admin.service.implementation;

import com.frodas.cine.admin.config.middleware.advice.TrackExecutionTime;
import com.frodas.cine.admin.config.redis.RedisHandler;
import com.frodas.cine.admin.persistence.entity.Config;
import com.frodas.cine.admin.persistence.repository.ConfigRepository;
import com.frodas.cine.admin.presentation.dto.ConfigDto;
import com.frodas.cine.admin.service.exception.BusinessException;
import com.frodas.cine.admin.service.interfaces.ConfigService;
import com.frodas.cine.admin.util.mapper.EntityMapper;
import io.micrometer.observation.annotation.Observed;
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
import java.util.UUID;
import java.util.stream.Collectors;

import static com.frodas.cine.admin.util.constants.ConstantsMessage.MSG_ID_NOT_FOUND;
import static com.frodas.cine.admin.util.constants.ConstantsRedisKeys.REDIS_CONFIG_KEY;
import static java.lang.Boolean.FALSE;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ConfigServiceImpl implements ConfigService {

    private final ConfigRepository configRepo;
    private final EntityMapper mapper;
    private final RedisTemplate<String, ConfigDto> redisConfigTemplate;
    private HashOperations<String, String, ConfigDto> hashOperations;

    @PostConstruct
    private void init() {
        hashOperations = redisConfigTemplate.opsForHash();
    }

    @Override
    @Transactional
    @TrackExecutionTime
    public ConfigDto registrar(ConfigDto dto) {
        Config entity = mapper.apiToEntity(dto);
        Config newEntity = configRepo.save(entity);
        deleteDataCache();
        return mapper.entityToApi(newEntity);
    }

    @Override
    @Transactional
    @TrackExecutionTime
    public ConfigDto actualizar(ConfigDto dto) {
        Config entity = mapper.apiToEntity(dto);
        entity.setIdConfig(dto.getIdConfig());
        Config newEntity = configRepo.save(entity);
        deleteDataCache();
        return mapper.entityToApi(newEntity);
    }

    @Override
    @Observed(name = "get.configs")
    public List<ConfigDto> listar() {
        RedisHandler<ConfigDto> redisHandler = RedisHandler.<ConfigDto>builder().key(REDIS_CONFIG_KEY).hashOperations(hashOperations).build();
        List<ConfigDto> data = redisHandler.findAll();
        if (data.isEmpty()) {
            List<Config> entityList = configRepo.findAll();
            data = mapper.entityListToApiList(entityList);
            Map<String, ConfigDto> dataCache = data.stream().collect(Collectors.toMap(x -> x.getIdConfig().toString(), x -> x));
            redisHandler.saveAll(dataCache);
        }
        return data;
    }

    @Override
    public Page<ConfigDto> listarPageable(Pageable pageable) {
        return configRepo.findAll(pageable).map(ConfigDto::new);
    }

    @Override
    public Page<ConfigDto> listarPageableNombreValor(String nombre, Pageable pageable) {
        return configRepo.findAllByParametroContainsIgnoreCaseOrValorContainsIgnoreCase(nombre, nombre, pageable).map(ConfigDto::new);
    }

    @Override
    @Observed(name = "get.config")
    public ConfigDto buscarPorId(UUID id) {
        Config entity = configRepo.findById(id)
                .orElseThrow(() -> new BusinessException(String.format(MSG_ID_NOT_FOUND, id)));
        return mapper.entityToApi(entity);
    }

    @Override
    @Observed(name = "get.configParam")
    public ConfigDto leerParametro(String param) {
        Config entity = configRepo.findByParametro(param);
        return mapper.entityToApi(entity);
    }

    @Override
    @Transactional
    public void eliminar(UUID id) {
        Config entity = configRepo.findById(id)
                .orElseThrow(() -> new BusinessException(String.format(MSG_ID_NOT_FOUND, id)));
        configRepo.deleteById(entity.getIdConfig());
        deleteDataCache();
    }

    public void eliminarOtro(UUID id) {
        configRepo.findById(id)
                .map(config -> {
                    configRepo.delete(config);
                    return true;
                })
                .orElseThrow(() -> new RuntimeException("No config at " + id));
    }

    @Override
    @Transactional
    public void updateEliminar(UUID id) {
        Config entity = configRepo.findById(id)
                .orElseThrow(() -> new BusinessException(String.format(MSG_ID_NOT_FOUND, id)));
        entity.setEstado(FALSE);
        configRepo.save(entity);
        deleteDataCache();
    }

    private void deleteDataCache() {
        RedisHandler<ConfigDto> redisHandler = RedisHandler.<ConfigDto>builder().key(REDIS_CONFIG_KEY).hashOperations(hashOperations).build();
        List<ConfigDto> data = redisHandler.findAll();
        for (ConfigDto datum : data) {
            redisHandler.delete(datum.getIdConfig().toString());
        }
    }

}
