package com.frodas.cine.admin.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class AppUtil {

    public static Integer totalPages(Integer totalItems, Integer itemsPorPagina) {
        Integer totalPaginas = totalItems / itemsPorPagina;
        if (totalItems % itemsPorPagina > 0)
            totalPaginas++;
        return totalPaginas;
    }

    public static String TO_JSON(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static <T> T JSON_TO_OBJECT(String json, Class<T> obj) {
        try {
            return new ObjectMapper().readValue(json, obj);
        } catch (IOException e) {
            log.info(e.getMessage());
            return null;
        }
    }

    public static String FILEPATH_TO_STRING(String pathFile) {
        try {
            return new String(Files.readAllBytes(Paths.get(pathFile)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
