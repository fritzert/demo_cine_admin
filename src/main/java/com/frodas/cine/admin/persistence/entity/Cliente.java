package com.frodas.cine.admin.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue
    private UUID idCliente;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "cliente")
    private Usuario usuario;

    @Column(name = "first_name", nullable = false, length = 80)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 80)
    private String lastName;

    @Column(name = "fecha_nac")
    private LocalDate fechaNac;

    @Column(name = "dni", length = 8, unique = true)
    private String dni;

}
