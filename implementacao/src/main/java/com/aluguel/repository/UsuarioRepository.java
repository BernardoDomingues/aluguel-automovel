package com.aluguel.repository;

import com.aluguel.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar por email
    Optional<Usuario> findByEmail(String email);

    // Buscar por nome (contém)
    List<Usuario> findByNomeContainingIgnoreCase(String nome);

    // Buscar usuários ativos
    List<Usuario> findByAtivoTrue();

    // Buscar usuários inativos
    List<Usuario> findByAtivoFalse();

    // Buscar por tipo de usuário
    @Query("SELECT u FROM Usuario u WHERE TYPE(u) = :tipoUsuario")
    List<Usuario> findByTipoUsuario(@Param("tipoUsuario") Class<? extends Usuario> tipoUsuario);

    // Buscar por endereço (contém)
    List<Usuario> findByEnderecoContainingIgnoreCase(String endereco);

    // Buscar por telefone
    Optional<Usuario> findByTelefone(String telefone);

    // Buscar usuários cadastrados em um período
    @Query("SELECT u FROM Usuario u WHERE u.dataCadastro BETWEEN :dataInicio AND :dataFim")
    List<Usuario> findByDataCadastroBetween(@Param("dataInicio") java.time.LocalDateTime dataInicio, 
                                           @Param("dataFim") java.time.LocalDateTime dataFim);

    // Contar usuários por tipo
    @Query("SELECT COUNT(u) FROM Usuario u WHERE TYPE(u) = :tipoUsuario")
    Long countByTipoUsuario(@Param("tipoUsuario") Class<? extends Usuario> tipoUsuario);

    // Contar usuários ativos
    Long countByAtivoTrue();

    // Verificar se email já existe (excluindo um ID específico)
    @Query("SELECT COUNT(u) > 0 FROM Usuario u WHERE u.email = :email AND u.id != :id")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("id") Long id);

    // Verificar se telefone já existe (excluindo um ID específico)
    @Query("SELECT COUNT(u) > 0 FROM Usuario u WHERE u.telefone = :telefone AND u.id != :id")
    boolean existsByTelefoneAndIdNot(@Param("telefone") String telefone, @Param("id") Long id);
}
