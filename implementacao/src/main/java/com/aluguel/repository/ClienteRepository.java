package com.aluguel.repository;

import com.aluguel.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByCpf(String cpf);

    Optional<Cliente> findByEmail(String email);

    Optional<Cliente> findByRg(String rg);

    @Query("SELECT c FROM Cliente c WHERE c.nome LIKE %:nome%")
    List<Cliente> findByNomeContaining(@Param("nome") String nome);

    @Query("SELECT c FROM Cliente c WHERE c.profissao LIKE %:profissao%")
    List<Cliente> findByProfissaoContaining(@Param("profissao") String profissao);

    @Query("SELECT c FROM Cliente c WHERE c.endereco LIKE %:endereco%")
    List<Cliente> findByEnderecoContaining(@Param("endereco") String endereco);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    boolean existsByRg(String rg);
}
