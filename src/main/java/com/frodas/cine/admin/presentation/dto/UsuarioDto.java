package com.frodas.cine.admin.presentation.dto;

import com.frodas.cine.admin.persistence.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto implements Serializable {

    private String fullName;

//    @MaskData
    //enmascarar datos PII (Personal Identifiable Information)
    private String email;

    //private String password;

    private boolean estado;

    //private List<Rol> roles;

    private String imagenName;

    private String imagenUrl;

    private String imagenId;

    public UsuarioDto(Usuario entity) {
        this.fullName = entity.getFullName();
        this.email = entity.getEmail();
        this.estado = entity.isEstado();
        this.imagenName = entity.getImagenName();
        this.imagenUrl = entity.getImagenUrl();
        this.imagenId = entity.getImagenId();
    }

}
