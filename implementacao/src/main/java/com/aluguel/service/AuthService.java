package com.aluguel.service;

import com.aluguel.dto.LoginRequest;
import com.aluguel.dto.LoginResponse;
import com.aluguel.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;

@Service
public class AuthService {

    private final UsuarioService usuarioService;

    public AuthService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
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

        String token = gerarToken(usuario);

        return new LoginResponse(
                token,
                usuario,
                usuario.getTipoUsuario(),
                "Login realizado com sucesso"
        );
    }

    public Usuario validarToken(String token) {
        try {
            String decodedToken = new String(Base64.getDecoder().decode(token));
            String[] parts = decodedToken.split(":");
            
            if (parts.length != 2) {
                throw new RuntimeException("Token inválido");
            }

            Long usuarioId = Long.parseLong(parts[0]);
            return usuarioService.buscarPorId(usuarioId)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        } catch (Exception e) {
            throw new RuntimeException("Token inválido");
        }
    }

    private String gerarToken(Usuario usuario) {
        String tokenData = usuario.getId() + ":" + UUID.randomUUID().toString();
        return Base64.getEncoder().encodeToString(tokenData.getBytes());
    }
}
