package com.foro.alura.hpg.api.domain.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionService implements UserDetailsService {

    @Autowired
    private IUsuarioRepository iUsuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return iUsuarioRepository.findByEmail(email);
    }
}
