package com.frodas.cine.admin.service.implementation;

import com.frodas.cine.admin.persistence.repository.UsuarioRepository;
import com.frodas.cine.admin.presentation.dto.ChangePasswordRequest;
import com.frodas.cine.admin.service.http.sendemail.EmailService;
import com.frodas.cine.admin.service.interfaces.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    //private final ResetTokenRepository resetTokenRepository;
    private final EmailService emailService;

    @Value("${app.mail.recovery.from-sender}")
    private String emailFrom;

    @Value("${app.mail.recovery.url}")
    private String emailURL;

    @Transactional
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        var securityUser = (SecurityUserImpl) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if (!passwordEncoder.matches(request.getCurrentPassword(), securityUser.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        var user = usuarioRepository.findByEmail(securityUser.getUsername()).get();
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        usuarioRepository.save(user);
    }

    @Override
    @Transactional
    public void sendEmail(String email) {
        var user = this.usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no existe"));
//        ResetToken token = new ResetToken();
//        token.setToken(UUID.randomUUID().toString());
//        token.setUsuario(user);
//        token.setExpiracion(30);
//        try {
//            resetTokenRepository.deleteByUsuarioId(user.getIdUsuario());
//            resetTokenRepository.save(token);
//            emailService.sendEmail(getMailDto(user, token));
//        } catch (Exception ex) {
//            throw new BusinessException("No se logró crear el token");
//        }
    }

//    private MailDto getMailDto(Usuario user, ResetToken token) {
//        MailDto mail = new MailDto();
//        mail.setFrom(emailFrom);
//        mail.setTo(user.getEmail());
//        mail.setSubject("RESTABLECER CONTRASEÑA - CINE ADMIN");
//
//        Map<String, Object> model = new HashMap<>();
//        String url = emailURL + token.getToken();
//        model.put("user", token.getUsuario().getFullName());
//        model.put("resetUrl", url);
//        mail.setModel(model);
//        return mail;
//    }

    @Override
    public Boolean verifyToken(String token) {
        Boolean exist = Boolean.FALSE;
//        try {
//            if (token != null && !token.isEmpty()) {
//                ResetToken rt = resetTokenRepository.findByToken(token);
//                if (rt != null && rt.getId() > 0 && !rt.isExpirado()) {
//                    exist = true;
//                }
//            }
//        } catch (Exception e) {
//            log.error("El token no existe o ha expirado : {}", e.getMessage());
//            throw new BusinessException("El token no existe o ha expirado");
//        }
        return exist;
    }

    @Override
    @Transactional
    public void restorePassword(String token, String password) {
//        ResetToken rt = resetTokenRepository.findByToken(token);
//        String claveHash = passwordEncoder.encode(password);
//        usuarioRepository.cambiarClave(claveHash, rt.getUsuario().getEmail());
//        resetTokenRepository.deleteById(rt.getId());
    }

}
