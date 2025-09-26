package com.aluguel.service;

import com.aluguel.model.Usuario;
import com.aluguel.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }


    public Usuario salvar(Usuario usuario) {
        if (usuario.getId() == null) {
            if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
                throw new RuntimeException("Email já cadastrado: " + usuario.getEmail());
            }
        } else {
            if (usuarioRepository.existsByEmailAndIdNot(usuario.getEmail(), usuario.getId())) {
                throw new RuntimeException("Email já cadastrado: " + usuario.getEmail());
            }
        }


        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(Long id, Usuario usuarioAtualizado) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setNome(usuarioAtualizado.getNome());
                    usuario.setEmail(usuarioAtualizado.getEmail());
                    usuario.setSenha(usuarioAtualizado.getSenha());
                    usuario.setEndereco(usuarioAtualizado.getEndereco());
                    return usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
    }

    public void ativar(Long id) {
        usuarioRepository.findById(id)
                .ifPresentOrElse(
                        usuario -> {
                            usuario.setAtivo(true);
                            usuarioRepository.save(usuario);
                        },
                        () -> {
                            throw new RuntimeException("Usuário não encontrado com ID: " + id);
                        }
                );
    }

    public void inativar(Long id) {
        usuarioRepository.findById(id)
                .ifPresentOrElse(
                        usuario -> {
                            usuario.setAtivo(false);
                            usuarioRepository.save(usuario);
                        },
                        () -> {
                            throw new RuntimeException("Usuário não encontrado com ID: " + id);
                        }
                );
    }

    public void excluir(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado com ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + email));
        
        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .authorities(new ArrayList<>())
                .build();
    }

}
