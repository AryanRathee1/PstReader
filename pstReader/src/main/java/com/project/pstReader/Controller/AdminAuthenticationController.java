package com.project.pstReader.Controller;

import com.project.pstReader.Model.AdminRequest.AdminRegisterRequest;
import com.project.pstReader.Model.Authentication.AuthenticationRequest;
import com.project.pstReader.Model.Authentication.AuthenticationResponse;
import com.project.pstReader.Service.AdminAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminAuthenticationController {

    private final AdminAuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AdminRegisterRequest request,
            @RequestParam String adminSecretKey
    ) {
        return ResponseEntity.ok(service.register(request,adminSecretKey));
    }

    // login endpoint
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

}
