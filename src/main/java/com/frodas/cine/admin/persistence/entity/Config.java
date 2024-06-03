package com.frodas.cine.admin.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "config")
public class Config extends Audit {

    @Id
    @GeneratedValue
    private UUID idConfig;

    @Column(name = "parametro", length = 5, nullable = false)
    private String parametro;

    @Column(name = "valor", length = 25)
    private String valor;

    @Column(name = "estado")
    private Boolean estado;

}
