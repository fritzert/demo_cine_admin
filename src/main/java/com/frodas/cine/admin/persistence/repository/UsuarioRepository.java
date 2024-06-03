package com.frodas.cine.admin.persistence.repository;

import com.frodas.cine.admin.persistence.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO usuario_rol (id_usuario, id_rol) VALUES (:idUsuario, :idRol)", nativeQuery = true)
    void registrarRolPorDefecto(@Param("idUsuario") UUID idUsuario, @Param("idRol") Integer idRol);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Usuario us SET us.password = :clave WHERE us.email = :email")
    void cambiarClave(@Param("clave") String clave, @Param("email") String email);

}
