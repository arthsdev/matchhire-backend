package br.com.artheus.matchhire.config;

import br.com.artheus.matchhire.infrastructure.security.CustomUserDetailsService;
import br.com.artheus.matchhire.infrastructure.security.JWTAuthorizationFilter;
import br.com.artheus.matchhire.infrastructure.security.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration class: configures JWT authorization filter,
 * password encoding, and public endpoints.
 */
@Configuration
public class SecurityConfig {

    private final TokenService tokenService;
    private final CustomUserDetailsService userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;

    public SecurityConfig(TokenService tokenService,
                          CustomUserDetailsService userDetailsService,
                          AuthenticationConfiguration authenticationConfiguration) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
        this.authenticationConfiguration = authenticationConfiguration;
    }

    /**
     * Expose the AuthenticationManager bean
     */
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Password encoder bean using BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configure HTTP security, JWT filters, and public endpoints
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Only JWTAuthorizationFilter is needed
        JWTAuthorizationFilter authorizationFilter =
                new JWTAuthorizationFilter(tokenService, userDetailsService);

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers("/api/auth/login", "/api/auth/refresh", "/api/users/register").permitAll()
                        // All other endpoints require authentication
                        .anyRequest().authenticated()
                )
                // Add authorization filter before UsernamePasswordAuthenticationFilter
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
