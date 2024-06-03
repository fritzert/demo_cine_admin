package com.frodas.cine.admin.persistence.repository;

import com.frodas.cine.admin.persistence.entity.Config;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConfigRepository extends JpaRepository<Config, UUID> {

    Config findByParametro(String param);

    Page<Config> findAllByParametroContainsIgnoreCaseOrValorContainsIgnoreCase(String parametro, String valor, Pageable pageable);

}
