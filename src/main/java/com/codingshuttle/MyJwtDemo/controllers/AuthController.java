package com.codingshuttle.MyJwtDemo.controllers;

import com.codingshuttle.MyJwtDemo.dto.LoginResponseDTO;
import com.codingshuttle.MyJwtDemo.dto.SignUpRequest;
import com.codingshuttle.MyJwtDemo.dto.UserDTO;
import com.codingshuttle.MyJwtDemo.dto.loginRequest;
import com.codingshuttle.MyJwtDemo.services.AuthService;
import com.codingshuttle.MyJwtDemo.services.SessionService;
import com.codingshuttle.MyJwtDemo.services.UserService;
import com.codingshuttle.MyJwtDemo.services.UserServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserServiceImpl userService;
    private final AuthService authService;
    private final SessionService sessionService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(userService.signUp(signUpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody loginRequest loginRequest, HttpServletResponse httpServletResponse){
       LoginResponseDTO loginResponseDTO = authService.login(loginRequest);

       sessionService.generateNewSession(loginResponseDTO.getUserId(),loginResponseDTO.getRefreshToken());

       httpServletResponse.addCookie(new Cookie("refreshToken",loginResponseDTO.getRefreshToken()));
       return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(HttpServletRequest httpServletRequest){
        String refreshToken = Arrays.stream(httpServletRequest.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findAny().orElseThrow(() -> new AuthenticationServiceException("Refresh token not found inside cookies"));

        sessionService.validateSession(refreshToken);

        LoginResponseDTO loginResponseDTO = authService.refresh(refreshToken);
        return ResponseEntity.ok(loginResponseDTO);
    }
}
