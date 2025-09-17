package com.aluguel.service;

import com.aluguel.model.Usuario;
import com.aluguel.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

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

    public List<Usuario> buscarPorNome(String nome) {
        return usuarioRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Usuario> buscarPorEndereco(String endereco) {
        return usuarioRepository.findByEnderecoContainingIgnoreCase(endereco);
    }

    public List<Usuario> listarAtivos() {
        return usuarioRepository.findByAtivoTrue();
    }

    public List<Usuario> listarInativos() {
        return usuarioRepository.findByAtivoFalse();
    }

    public Usuario salvar(Usuario usuario) {
        // Validar se email já existe
        if (usuario.getId() == null) {
            if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
                throw new RuntimeException("Email já cadastrado: " + usuario.getEmail());
            }
        } else {
            if (usuarioRepository.existsByEmailAndIdNot(usuario.getEmail(), usuario.getId())) {
                throw new RuntimeException("Email já cadastrado: " + usuario.getEmail());
            }
        }

        // Validar se telefone já existe
        if (usuario.getId() == null) {
            if (usuarioRepository.findByTelefone(usuario.getTelefone()).isPresent()) {
                throw new RuntimeException("Telefone já cadastrado: " + usuario.getTelefone());
            }
        } else {
            if (usuarioRepository.existsByTelefoneAndIdNot(usuario.getTelefone(), usuario.getId())) {
                throw new RuntimeException("Telefone já cadastrado: " + usuario.getTelefone());
            }
        }

        // Definir data de cadastro se for novo usuário
        if (usuario.getId() == null) {
            usuario.setDataCadastro(LocalDateTime.now());
        } else {
            usuario.setUltimaAtualizacao(LocalDateTime.now());
        }

        // Criptografar senha (em produção usar BCrypt)
        // Por simplicidade, vamos manter a senha em texto plano

        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(Long id, Usuario usuarioAtualizado) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setNome(usuarioAtualizado.getNome());
                    usuario.setEmail(usuarioAtualizado.getEmail());
                    usuario.setSenha(usuarioAtualizado.getSenha());
                    usuario.setTelefone(usuarioAtualizado.getTelefone());
                    usuario.setEndereco(usuarioAtualizado.getEndereco());
                    usuario.setObservacoes(usuarioAtualizado.getObservacoes());
                    usuario.setUltimaAtualizacao(LocalDateTime.now());
                    return usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
    }

    public void ativar(Long id) {
        usuarioRepository.findById(id)
                .ifPresentOrElse(
                        usuario -> {
                            usuario.setAtivo(true);
                            usuario.setUltimaAtualizacao(LocalDateTime.now());
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
                            usuario.setUltimaAtualizacao(LocalDateTime.now());
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

    public List<Usuario> buscarPorPeriodoCadastro(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return usuarioRepository.findByDataCadastroBetween(dataInicio, dataFim);
    }

    public Long contarUsuariosAtivos() {
        return usuarioRepository.countByAtivoTrue();
    }

    public Long contarUsuariosPorTipo(Class<? extends Usuario> tipoUsuario) {
        return usuarioRepository.countByTipoUsuario(tipoUsuario);
    }

    public boolean emailExiste(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }

    public boolean telefoneExiste(String telefone) {
        return usuarioRepository.findByTelefone(telefone).isPresent();
    }
}
