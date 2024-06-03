package com.frodas.cine.admin.service.interfaces;

public interface UsuarioService {

    void sendEmail(String email);

    Boolean verifyToken(String token);

    void restorePassword(String token, String password);

}
