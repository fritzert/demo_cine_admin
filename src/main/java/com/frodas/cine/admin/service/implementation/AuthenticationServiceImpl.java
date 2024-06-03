package com.frodas.cine.admin.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frodas.cine.admin.config.security.JwtService;
import com.frodas.cine.admin.persistence.entity.Cliente;
import com.frodas.cine.admin.persistence.entity.Usuario;
import com.frodas.cine.admin.persistence.repository.UsuarioRepository;
import com.frodas.cine.admin.presentation.dto.AuthenticationRequest;
import com.frodas.cine.admin.presentation.dto.AuthenticationResponse;
import com.frodas.cine.admin.presentation.dto.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AuthenticationServiceImpl {

    private final UsuarioRepository usuarioRepository;
    //private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Value("${app.default.rol}")
    private Integer DEFAULT_ROL;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        var user = Usuario.builder()
                .fullName(request.getFirstName() + " " + request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .estado(Boolean.TRUE)
                .build();
        var cliente = Cliente.builder().firstName(request.getFirstName()).lastName(request.getLastName()).build();
        user.setCliente(cliente);
        cliente.setUsuario(user);

        var savedUser = usuarioRepository.save(user);
        usuarioRepository.registrarRolPorDefecto(savedUser.getIdUsuario(), DEFAULT_ROL);

        var securityUser = usuarioRepository.findByEmail(savedUser.getEmail())
                .map(SecurityUserImpl::build)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no existe"));
        var jwtToken = jwtService.generateToken(securityUser);
        var refreshToken = jwtService.generateRefreshToken(securityUser);
        //saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .fullName(user.getFullName()).email(user.getEmail())
                .accessToken(jwtToken).refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = usuarioRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("Usuario no existe"));
        var securityUser = SecurityUserImpl.build(user);
        var jwtToken = jwtService.generateToken(securityUser);
        var refreshToken = jwtService.generateRefreshToken(securityUser);
        //revokeAllUserTokens(user);
        //saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .fullName(user.getFullName()).email(user.getEmail())
                .imagenUrl(user.getImagenUrl())
                .accessToken(jwtToken).refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public void refreshToken(HttpServletRequest request, HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.replace("Bearer ", "");
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.usuarioRepository.findByEmail(userEmail).orElseThrow();
            var securityUser = SecurityUserImpl.build(user);
            if (jwtService.isTokenValid(refreshToken, securityUser)) {
                var accessToken = jwtService.generateToken(securityUser);
                //revokeAllUserTokens(user);
                //saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .fullName(user.getFullName()).email(user.getEmail())
                        .imagenUrl(user.getImagenUrl())
                        .accessToken(accessToken).refreshToken(refreshToken)
                        .build();
                response.setContentType(APPLICATION_JSON_VALUE);
                response.setStatus(HttpServletResponse.SC_OK);
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    /*
    private void saveUserToken(Usuario user, String jwtToken) {
        var token = Token.builder()
                .usuario(user)
                .accessToken(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(Usuario user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getIdUsuario());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
    */

}
