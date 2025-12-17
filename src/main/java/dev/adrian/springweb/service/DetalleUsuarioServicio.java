package dev.adrian.springweb.service;

import dev.adrian.springweb.model.Usuario;
import dev.adrian.springweb.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DetalleUsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("Iniciando usuario con el usuario {}", username);

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.info("Usuario no encontrado: " + username);
                    return new UsernameNotFoundException("Usuario no encontrado");
                });

        log.info("Terminando usuario con el usuario {}", username);

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(usuario.getRol())
                .build();
    }

    public void registrarNuevoUsuario(String username, String password ) {
        if (usuarioRepository.findByUsername(username).isPresent()) {
            throw new UsernameNotFoundException("Usuario existente");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(username);

        usuario.setPassword(password);
        usuarioRepository.save(usuario);

        usuarioRepository.save(usuario);
    }
}