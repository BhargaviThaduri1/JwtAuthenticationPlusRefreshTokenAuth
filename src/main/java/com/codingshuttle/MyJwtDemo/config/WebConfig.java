package com.codingshuttle.MyJwtDemo.config;

import com.codingshuttle.MyJwtDemo.entities.enums.Permission;
import com.codingshuttle.MyJwtDemo.filters.JwtFilter;
import com.codingshuttle.MyJwtDemo.handlers.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.codingshuttle.MyJwtDemo.entities.enums.Role.ADMIN;
import static com.codingshuttle.MyJwtDemo.entities.enums.Role.CREATOR;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class WebConfig {

    private final JwtFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    private static final String[] publicRoutes = {
            "/error", "/auth/**", "/home.html"
    };
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http){
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/error","/auth/**","/home.html").permitAll()
                        .requestMatchers(HttpMethod.GET,"/posts/**").permitAll()
                       // .requestMatchers(HttpMethod.POST,"/posts/**").hasAnyRole(ADMIN.name(),CREATOR.name())
                       // .requestMatchers(HttpMethod.POST,"/posts/**").hasAnyAuthority(Permission.POST_CREATE.name())
                       // .requestMatchers(HttpMethod.GET,"/posts/**").hasAnyAuthority(Permission.POST_VIEW.name())
                        .requestMatchers(HttpMethod.DELETE,"/posts/**").hasAnyAuthority(Permission.POST_DELETE.name())
                        .requestMatchers(HttpMethod.PUT,"/posts/**").hasAnyAuthority(Permission.POST_UPDATE.name())
                        .anyRequest().authenticated()
                       )
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionConfig -> sessionConfig
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2login -> oauth2login
                        .failureUrl("/login?error=true")
                        .successHandler(oAuth2SuccessHandler)
                );

        return http.build();
    }
    @Bean
    AuthenticationManager getAuthenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

}
