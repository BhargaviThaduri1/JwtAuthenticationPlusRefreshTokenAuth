package com.codingshuttle.MyJwtDemo.handlers;

import com.codingshuttle.MyJwtDemo.dto.LoginResponseDTO;
import com.codingshuttle.MyJwtDemo.entities.UserEntity;
import com.codingshuttle.MyJwtDemo.services.JwtService;
import com.codingshuttle.MyJwtDemo.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) token.getPrincipal();

        log.info(oAuth2User.getAttribute("email"));

        String email = oAuth2User.getAttribute("email");
        UserEntity user = userService.getUserByEmail(email);

        if(user == null){
            UserEntity newUser = user.builder()
                    .username(oAuth2User.getAttribute("name"))
                    .email(email)
                    .build();
            user = userService.save(newUser);
            log.info("User saved");
        }
        LoginResponseDTO loginResponseDTO = jwtService.generateToken(user);

        Cookie cookie = new Cookie("refreshToken",loginResponseDTO.getRefreshToken());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        String frontEndUrl = "http://localhost:8080/home.html?token="+loginResponseDTO.getAccessToken();
        getRedirectStrategy().sendRedirect(request,response,frontEndUrl);


    }
}
