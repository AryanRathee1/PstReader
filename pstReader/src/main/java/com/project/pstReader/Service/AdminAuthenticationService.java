package com.project.pstReader.Service;

import com.project.pstReader.Model.AdminRequest.AdminRegisterRequest;
import com.project.pstReader.Model.Authentication.AuthenticationRequest;
import com.project.pstReader.Model.Authentication.AuthenticationResponse;
import com.project.pstReader.Model.Entity.Token;
import com.project.pstReader.Model.Entity.TokenType;
import com.project.pstReader.Model.Entity.User;
import com.project.pstReader.Repository.TokenRepository;
import com.project.pstReader.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminAuthenticationService {

    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;

    public AuthenticationResponse register(AdminRegisterRequest request, String adminSecretKey) {
        if( !adminSecretKey.equals("admin") ){
            throw new RuntimeException("Invalid Admin Secret Key");
        }

        var admin = User.builder()
                .userId(request.getUserId())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .managerId(request.getManagerId())
                .build();

        var savedUser = repository.save(admin);
        var jwtToken = jwtService.generateToken(admin);
        saveUserToken(savedUser, jwtToken);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(user, jwtToken);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
