package com.convivir.app.config;

import com.convivir.app.security.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    private static final String[] PUBLIC_URLS = {
            "/", "/inicio", "/noticias/**", "/noticia/ver/**", "/api/noticias/**",
            "/uploads/**", "/zonas-comunes", "/contacto", "/informacion",
            "/login", "/registro", "/recuperar-password/**",
            "/css/**", "/js/**", "/images/**", "/webjars/**", "/api/inicio/**",
            "/acceso-denegado"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) 
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(PUBLIC_URLS).permitAll()
                
                // Reglas de acceso por Rol
                .requestMatchers("/admin/financiero/**", "/admin/reportes/**").hasRole("ADMIN")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/residente/mis-pagos/**").hasRole("RESIDENTE")
                .requestMatchers("/residente/**").hasRole("RESIDENTE")
                .requestMatchers("/propietario/**").hasAnyRole("PROPIETARIO", "ADMIN")
                .requestMatchers("/portero/**").hasRole("PORTERO")
                .requestMatchers("/proveedor/**").hasRole("PROVEEDOR")
                
                .anyRequest().authenticated()
            )
            .exceptionHandling(exception -> exception
                .accessDeniedPage("/acceso-denegado") 
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
            )
            .formLogin(login -> login
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("correo")
                .passwordParameter("password")
                .successHandler((request, response, authentication) -> {
                    String rol = authentication.getAuthorities().iterator().next().getAuthority();
                    // Redirección corregida al panel de portería
                    switch (rol) {
                        case "ROLE_ADMIN" -> response.sendRedirect("/admin/dashboard");
                        case "ROLE_RESIDENTE" -> response.sendRedirect("/residente/dashboard");
                        case "ROLE_PROPIETARIO" -> response.sendRedirect("/propietario/dashboard");
                        case "ROLE_PORTERO" -> response.sendRedirect("/portero/ingresos-hoy"); 
                        case "ROLE_PROVEEDOR" -> response.sendRedirect("/proveedor/dashboard");
                        default -> response.sendRedirect("/login?error");
                    }
                })
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}