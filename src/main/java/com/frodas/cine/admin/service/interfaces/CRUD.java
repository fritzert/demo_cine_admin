package com.frodas.cine.admin.service.interfaces;

import java.util.List;
import java.util.UUID;

public interface CRUD<T> {

    T registrar(T obj);

    T actualizar(T obj);

    List<T> listar();

    T buscarPorId(UUID id);

    void eliminar(UUID id);

    void updateEliminar(UUID id);

}
