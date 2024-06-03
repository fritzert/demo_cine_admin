package com.frodas.cine.admin.presentation.controller;

import com.frodas.cine.admin.presentation.doc.AccountRecoveryDoc;
import com.frodas.cine.admin.service.interfaces.UsuarioService;
import com.frodas.cine.admin.presentation.advice.SuccesResponse;
import com.frodas.cine.admin.presentation.advice.SuccesResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.frodas.cine.admin.util.constants.ConstantsPath.PATH_RECOVERY;

@RestController
@RequestMapping(PATH_RECOVERY)
@RequiredArgsConstructor
public class AccountRecoveryController implements AccountRecoveryDoc {

    private final UsuarioService usuarioService;

    @Override
    @PostMapping(value = "/send-email")
    public ResponseEntity<SuccesResponse<Void>> enviarCorreo(@RequestBody String email) {
        usuarioService.sendEmail(email);
        return SuccesResponseHandler.SUCCESS();
    }

    @Override
    @GetMapping(value = "/verify/{token}")
    public ResponseEntity<SuccesResponse<Boolean>> verificarToken(@PathVariable("token") String token) {
        Boolean rpta = usuarioService.verifyToken(token);
        return SuccesResponseHandler.SUCCESS(rpta);
    }

    @Override
    @PostMapping(value = "/restore/{token}")
    public ResponseEntity<SuccesResponse<Void>> restablecerClave(
            @PathVariable("token") String token,
            @RequestBody String password) {
        usuarioService.restorePassword(token, password);
        return SuccesResponseHandler.SUCCESS();
    }

}
