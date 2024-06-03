package com.frodas.cine.admin.persistence.repository;

import com.frodas.cine.admin.persistence.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    @Query(value = """
            select t from Cliente t inner join Usuario u
            on t.usuario.idUsuario = u.idUsuario
            where LOWER(t.firstName) like %:nombre% or LOWER(t.lastName) like %:nombre%
            or LOWER(t.dni) like %:nombre% or LOWER(t.usuario.email) like %:nombre%
            """)
    Page<Cliente> buscarDatosNombresEmail(@Param("nombre") String nombre, Pageable pageable);

    @Query(value = """
            select distinct r.id_rol, r.nombre, r.descripcion, ur.id_usuario
            from usuario_rol ur
            inner join roles r on r.id_rol = ur.id_rol
            where ur.id_usuario = :idCliente
             """, nativeQuery = true)
    List<Object[]> listarUsuarioRolByMenu(@Param("idCliente") UUID idCliente);

    @Modifying
    @Query(value = "delete from usuario_rol ur where ur.id_usuario = :idCliente", nativeQuery = true)
    void deleteUsuarioRol(@Param("idCliente") UUID idCliente);

    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO usuario_rol (id_usuario, id_rol) VALUES (:idCliente, :idRol)", nativeQuery = true)
    void registrarUsuarioRol(@Param("idCliente") UUID idCliente, @Param("idRol") Integer idRol);

    @Query(value = """
            select t from Cliente t inner join Usuario u
            on t.usuario.idUsuario = u.idUsuario
            where LOWER(t.usuario.email) like %:email%
            """)
    Cliente findByEmail(@Param("email") String email);

    Optional<Cliente> findByDni(@Param("dni") String dni);

}
