package com.frodas.cine.admin.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Rol {

    @Id
    @GeneratedValue
    @Column(name = "id_rol")
    private Integer idRol;

    @Column(name = "nombre", length = 30)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

}
