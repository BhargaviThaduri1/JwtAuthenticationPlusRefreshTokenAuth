package com.codingshuttle.MyJwtDemo.services;

import com.codingshuttle.MyJwtDemo.dto.LoginResponseDTO;
import com.codingshuttle.MyJwtDemo.dto.loginRequest;
import com.codingshuttle.MyJwtDemo.entities.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuthService {

    AuthenticationManager authenticationManager;
    JwtService jwtService;
    UserService userService;

    public LoginResponseDTO login(loginRequest loginRequest) {
        System.out.println("INside login");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        UserEntity user = (UserEntity) authentication.getPrincipal();
        System.out.println("USER "+ user);
        return jwtService.generateToken(user);
    }

    public LoginResponseDTO refresh(String refreshToken) {
        Long userId = jwtService.getUserId(refreshToken);
        UserEntity user = userService.getUserById(userId);
        return jwtService.generateToken(user);
    }
}
