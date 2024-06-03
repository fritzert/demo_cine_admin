package com.frodas.cine.admin.config.redis;

import lombok.Builder;
import org.springframework.data.redis.core.HashOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Builder
public class RedisHandler<T> {

    private String key;
    private HashOperations<String, String, T> hashOperations;

    public List<T> findAll() {
        List<T> lista = new ArrayList<>();
        Map<String, T> response = hashOperations.entries(key);
        response.forEach((k, v) -> lista.add(v));
        return lista;
    }

    public T findById(String id) {
        return hashOperations.get(key, id);
    }

    public void save(String id, T entity) {
        hashOperations.put(key, id, entity);
    }

    public void saveAll(Map<String, T> listaMap) {
        hashOperations.putAll(key, listaMap);
    }

    public void delete(String id) {
        hashOperations.delete(key, id);
    }

}
