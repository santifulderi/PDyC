package ar.edu.unnoba.pdyc2024.mymusic.security;

import jakarta.ws.rs.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, UserDetailsService userDetailsService) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = authenticationManager(authenticationConfiguration);
        http
                .csrf(csrf -> csrf.disable())
                .userDetailsService(userDetailsService)
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(HttpMethod.GET, "/songs/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/playlists/**").permitAll()
                                .requestMatchers("/login").permitAll()
                                .anyRequest().fullyAuthenticated()
                )
                .addFilter(new JWTAuthenticationFilter(
                        authenticationConfiguration.getAuthenticationManager()
                ))
                .addFilter(new JWTAuthorizationFilter(
                        authenticationConfiguration.getAuthenticationManager()
                ));
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
