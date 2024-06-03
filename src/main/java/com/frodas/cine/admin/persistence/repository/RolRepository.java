package com.frodas.cine.admin.persistence.repository;

import com.frodas.cine.admin.persistence.entity.Rol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Integer> {

    Page<Rol> findAllByNombreContainsIgnoreCaseOrDescripcionContainsIgnoreCase(String nombre, String descripcion, Pageable pageable);

}
