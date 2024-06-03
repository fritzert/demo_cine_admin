package com.frodas.cine.admin.presentation.controller;

import com.frodas.cine.admin.presentation.dto.AuthenticationResponse;
import com.frodas.cine.admin.service.implementation.OAuthServiceImpl;
import com.frodas.cine.admin.presentation.advice.SuccesResponse;
import com.frodas.cine.admin.presentation.advice.SuccesResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static com.frodas.cine.admin.util.constants.ConstantsPath.PATH_OAUTH;

@Controller
@RequestMapping(PATH_OAUTH)
@RequiredArgsConstructor
public class OAuthController {//implements AuthDoc {

    private final OAuthServiceImpl oAuthService;

    @GetMapping("/google")
    public ResponseEntity<SuccesResponse<AuthenticationResponse>> google(@RequestParam("token") String token) throws GeneralSecurityException, IOException {
        AuthenticationResponse response = oAuthService.authenticateGoogle(token);
        return SuccesResponseHandler.SUCCESS(response);
    }

//    @GetMapping("/facebook")
//    public ResponseEntity<TokenDTO> facebook(@RequestParam("token") String token) throws IOException {
//        Facebook facebook = new FacebookTemplate(tokenDto.getValue());
//        final String[] fields = { "email", "picture" };
//        User user = facebook.fetchObject("me", User.class, fields);
//    }

}
