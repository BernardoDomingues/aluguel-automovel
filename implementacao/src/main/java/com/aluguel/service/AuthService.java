package com.aluguel.service;

import com.aluguel.config.JwtUtil;
import com.aluguel.dto.LoginRequest;
import com.aluguel.dto.LoginResponse;
import com.aluguel.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthService(UsuarioService usuarioService, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Usuario usuario = usuarioService.buscarPorEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Email ou senha inválidos"));

        if (!usuario.getAtivo()) {
            throw new RuntimeException("Usuário inativo");
        }

        if (!usuario.getSenha().equals(loginRequest.getSenha())) {
            throw new RuntimeException("Email ou senha inválidos");
        }

        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getId(), usuario.getTipoUsuario());

        return new LoginResponse(
                token,
                usuario,
                usuario.getTipoUsuario(),
                "Login realizado com sucesso"
        );
    }

    public Usuario validarToken(String token) {
        try {
            String email = jwtUtil.extractUsername(token);
            Long usuarioId = jwtUtil.extractUserId(token);
            
            if (jwtUtil.validateToken(token, email)) {
                return usuarioService.buscarPorId(usuarioId)
                        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            } else {
                throw new RuntimeException("Token inválido");
            }
        } catch (Exception e) {
            throw new RuntimeException("Token inválido");
        }
    }
}
