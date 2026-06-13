package com.convivir.app.service;

import com.convivir.app.dto.LoginRequest;
import com.convivir.app.dto.LoginResponse;
import com.convivir.app.model.Usuario;
import com.convivir.app.repository.UsuarioRepository;
import com.convivir.app.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Correo o contraseña incorrectos"));

        if (!usuario.isEstado()) {
            throw new RuntimeException("Usuario inactivo");
        }

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPasswordHash())) {
            throw new RuntimeException("Correo o contraseña incorrectos");
        }

        String token = jwtUtil.generateToken(
                usuario.getCorreo(),
                usuario.getRol().name()
        );

        return new LoginResponse(token, usuario.getRol().name(),
                usuario.getCorreo(), "Login exitoso");
    }

    public String getDashboardByRol(String rol) {
        return switch (rol) {
            case "ADMIN" -> "/admin/dashboard";
            case "RESIDENTE" -> "/residente/dashboard";
            case "PROPIETARIO" -> "/propietario/dashboard";
            case "PORTERO" -> "/portero/dashboard";
            case "PROVEEDOR" -> "/proveedor/dashboard";
            default -> "/login";
        };
    }
}