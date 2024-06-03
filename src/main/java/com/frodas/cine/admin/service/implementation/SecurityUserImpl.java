package com.frodas.cine.admin.service.implementation;

import com.frodas.cine.admin.persistence.entity.Usuario;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@ToString
public class SecurityUserImpl implements UserDetails {

    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private String imagenUrl;

    public SecurityUserImpl(String email, String password, Collection<? extends GrantedAuthority> authorities, String imagenUrl) {
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.imagenUrl = imagenUrl;
    }

    public static SecurityUserImpl build(Usuario usuario) {
        List<GrantedAuthority> authorities =
                usuario.getRoles().stream()
                        .map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
                        .collect(Collectors.toList());
        return new SecurityUserImpl(usuario.getEmail(), usuario.getPassword(), authorities, usuario.getImagenUrl());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
