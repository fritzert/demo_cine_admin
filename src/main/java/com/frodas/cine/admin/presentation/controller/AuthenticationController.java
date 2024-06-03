package com.frodas.cine.admin.presentation.controller;

import com.frodas.cine.admin.presentation.doc.AuthenticationDoc;
import com.frodas.cine.admin.presentation.dto.AuthenticationRequest;
import com.frodas.cine.admin.presentation.dto.AuthenticationResponse;
import com.frodas.cine.admin.presentation.dto.RegisterRequest;
import com.frodas.cine.admin.service.implementation.AuthenticationServiceImpl;
import com.frodas.cine.admin.presentation.advice.SuccesResponse;
import com.frodas.cine.admin.presentation.advice.SuccesResponseHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.frodas.cine.admin.util.constants.ConstantsPath.PATH_AUTH;

@RestController
@RequestMapping(PATH_AUTH)
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationDoc {

    private final AuthenticationServiceImpl authenticationService;

    @Override
    @PostMapping("/register")
    public ResponseEntity<SuccesResponse<AuthenticationResponse>> register(@RequestBody RegisterRequest request) {
        AuthenticationResponse response = authenticationService.register(request);
        return SuccesResponseHandler.SUCCESS(HttpStatus.CREATED.value(), response);
    }

    @Override
    @PostMapping("/login")//signin
    public ResponseEntity<SuccesResponse<AuthenticationResponse>> login(@Valid @RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = authenticationService.authenticate(request);
        return SuccesResponseHandler.SUCCESS(response);
    }

    @GetMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }

}
