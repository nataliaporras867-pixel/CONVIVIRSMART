package com.convivir.app.security;

import com.convivir.app.model.Usuario;
import com.convivir.app.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo)
            throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuario no encontrado: " + correo));

        if (!usuario.isEstado()) {
            throw new UsernameNotFoundException("Usuario inactivo: " + correo);
        }

        return new org.springframework.security.core.userdetails.User(
                usuario.getCorreo(),
                usuario.getPasswordHash(),
                List.of(new SimpleGrantedAuthority(
                        "ROLE_" + usuario.getRol().name()))
        );
    }
}