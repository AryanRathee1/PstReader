package com.project.pstReader.Service;

import com.project.pstReader.Model.ChangePasswordRequest.ChangePasswordRequest;
import com.project.pstReader.Model.Entity.User;
import com.project.pstReader.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if( !passwordEncoder.matches(request.getCurrentPassword(),user.getPassword()) ){
            throw new IllegalStateException("Wrong password");
        }

        if( !request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new IllegalStateException("Passwords are not the same");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        repository.save(user);
    }
}
