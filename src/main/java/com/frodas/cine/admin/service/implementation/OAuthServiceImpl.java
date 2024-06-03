package com.frodas.cine.admin.service.implementation;

import com.frodas.cine.admin.config.security.JwtService;
import com.frodas.cine.admin.persistence.entity.Cliente;
import com.frodas.cine.admin.persistence.entity.Usuario;
import com.frodas.cine.admin.persistence.repository.UsuarioRepository;
import com.frodas.cine.admin.presentation.dto.AuthenticationResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class OAuthServiceImpl {

    private final UsuarioRepository usuarioRepository;
    //private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Value("${app.default.rol}")
    private Integer DEFAULT_ROL;

    @Value("${app.default.secretpass}")
    private String DEFAULT_SECRETPASS;

    @Value("${app.oauth.google.clientId}")
    private String GOOGLE_CLIENTID;

    @Transactional
    public AuthenticationResponse authenticateGoogle(String token) throws GeneralSecurityException, IOException {
        Payload payload = getPayloadGoogle(token);
        if (payload.getEmail().isBlank()) {
            throw new UsernameNotFoundException("Email no existe");
        }
        // Print user identifier
        String userId = payload.getSubject();
        // Get profile information from payload
        String email = payload.getEmail();
        //boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());

        //String name = (String) payload.get("name");
        String pictureUrl = (String) payload.get("picture");
        //String locale = (String) payload.get("locale");
        String givenName = (String) payload.get("given_name");
        String familyName = (String) payload.get("family_name");

//        payload
//        {
//            "iss": "https://accounts.google.com", // The JWT's issuer
//            "nbf":  161803398874,
//            "aud": "314159265-pi.apps.googleusercontent.com", // Your server's client ID
//            "sub": "3141592653589793238", // The unique ID of the user's Google Account
//            "hd": "gmail.com", // If present, the host domain of the user's GSuite email address
//            "email": "elisa.g.beckett@gmail.com", // The user's email address
//            "email_verified": true, // true, if Google has verified the email address
//            "azp": "314159265-pi.apps.googleusercontent.com",
//            "name": "Elisa Beckett",
//            // If present, a URL to user's profile picture
//            "picture": "https://lh3.googleusercontent.com/a-/e2718281828459045235360uler",
//            "given_name": "Eliza",
//            "family_name": "Beckett",
//            "iat": 1596474000, // Unix timestamp of the assertion's creation time
//            "exp": 1596477600, // Unix timestamp of the assertion's expiration time
//            "jti": "abc161803398874def"
//        }

        Optional<Usuario> opUsuario = usuarioRepository.findByEmail(payload.getEmail());
        Usuario user = opUsuario.orElseGet(() -> createUser(givenName, familyName, email, pictureUrl, userId));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getEmail(), DEFAULT_SECRETPASS));
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

    private Payload getPayloadGoogle(String token) throws GeneralSecurityException, IOException {
        if (token == null) {
            throw new UsernameNotFoundException("Token no existe");
        }
        NetHttpTransport netHttpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = new GsonFactory();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(netHttpTransport, jsonFactory)
                //.setAudience(Arrays.asList("your-client-id-1", "your-client-id-2"))
                .setAudience(Arrays.asList(GOOGLE_CLIENTID))
                .build();
        GoogleIdToken googleIdToken = verifier.verify(token);
        Payload payload = googleIdToken.getPayload();
        return payload;
    }

    private Usuario createUser(String givenName, String familyName, String email, String pictureUrl, String userId) {
        var newUser = Usuario.builder()
                .fullName(givenName + " " + familyName)
                .email(email)
                .password(passwordEncoder.encode(DEFAULT_SECRETPASS))
                .imagenId(userId)
                .imagenName(givenName + " " + familyName)
                .imagenUrl(pictureUrl)
                .estado(Boolean.TRUE)
                .build();
        var cliente = Cliente.builder().firstName(givenName).lastName(familyName).build();
        newUser.setCliente(cliente);
        cliente.setUsuario(newUser);

        var user = usuarioRepository.save(newUser);
        usuarioRepository.registrarRolPorDefecto(user.getIdUsuario(), DEFAULT_ROL);
        var userFinal = usuarioRepository.findByEmail(email).get();
        return userFinal;
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
