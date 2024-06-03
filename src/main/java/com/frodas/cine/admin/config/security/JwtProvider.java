package com.frodas.cine.admin.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.frodas.cine.admin.service.implementation.SecurityUserImpl;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;
import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

// TODO: ELIMINAR YA QUE NO SE USA, ES UNA ALTERNATIVA AL JwtService
//@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {

    @Value("${app.security.jwt.secret-key}")
    private String secretKey;

    @Value("${app.security.jwt.expiration-ms}")
    private Integer timeMs;

    @Value("${app.security.jwt.refresh-token.expiration-ms}")
    private Integer timeRefreshMs;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @PostConstruct
    protected void init() {
        // esto es para evitar tener la clave secreta sin procesar disponible en la JVM
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String type, String url, Authentication authentication) {
        SecurityUserImpl user = (SecurityUserImpl) authentication.getPrincipal();
        List<String> nomRoles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        Date now = new Date();
        Date validity = new Date(now.getTime() + timeMs); // 10 minutos
        Date validityRefresh = new Date(now.getTime() + timeRefreshMs); // 10 minutos
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());

        String token;
        if (type.equalsIgnoreCase("access")) {
            token = JWT.create()
                    .withSubject(user.getUsername())
                    .withClaim("roles", nomRoles)
                    .withIssuer(url)
                    .withIssuedAt(now)
                    .withExpiresAt(validity)
                    .sign(algorithm);
            //System.out.println("token acc: " + token);
            return token;
        }
        if (type.equalsIgnoreCase("refresh")) {
            token = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(validityRefresh)
                    .withIssuer(url)
                    .sign(algorithm);
            //System.out.println("token ref: " + token);
            return token;
        }
        return "";
    }

    public DecodedJWT getDecodeJWT(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    public String getNombreUsuarioFromToken(String token) {
        return getDecodeJWT(token).getIssuer();
    }

    public boolean validateToken(String token) {
        try {
            getDecodeJWT(token).getPayload();
        } catch (JWTVerificationException ex) {
            log.error("Error en verificacion de Token");
        }
        return true;
    }

    public void sendTokenResponse(HttpServletResponse response, String access_token, String refresh_token) throws IOException {
        // esto es para enviar en el body
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        response.setContentType(APPLICATION_JSON_VALUE);
        OBJECT_MAPPER.writeValue(response.getOutputStream(), tokens);
    }

    public void sendErrorResponse(HttpServletResponse response, String msg) throws IOException {
//        response.setHeader("error", msg);
//        response.setStatus(FORBIDDEN.value());
//        //response.sendError(FORBIDDEN.value());
//        Map<String, String> error = new HashMap<>();
//        error.put("error_message", msg);
//        response.setContentType(APPLICATION_JSON_VALUE);
//        new ObjectMapper().writeValue(response.getOutputStream(), error);

        // token invalido
        final Map<String, Object> mapException = new HashMap<>();
        mapException.put("message", "Acceso no autorizado");
        mapException.put("exception", msg);
        mapException.put("timestamp", (new Date()).getTime());
        mapException.put("statusCode", HttpServletResponse.SC_UNAUTHORIZED);
        //mapException.put("path", request.getServletPath());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        OBJECT_MAPPER.writeValue(response.getOutputStream(), mapException);
    }
}