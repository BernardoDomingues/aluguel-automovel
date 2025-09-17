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

    // Buscar por CPF
    Optional<Cliente> findByCpf(String cpf);

    // Buscar por RG
    Optional<Cliente> findByRg(String rg);

    // Buscar por profissão (contém)
    List<Cliente> findByProfissaoContainingIgnoreCase(String profissao);

    // Buscar por nome (contém)
    List<Cliente> findByNomeContainingIgnoreCase(String nome);

    // Buscar por email
    Optional<Cliente> findByEmail(String email);

    // Buscar por telefone
    Optional<Cliente> findByTelefone(String telefone);

    // Buscar por endereço (contém)
    List<Cliente> findByEnderecoContainingIgnoreCase(String endereco);

    // Buscar clientes ativos
    List<Cliente> findByAtivoTrue();

    // Buscar clientes inativos
    List<Cliente> findByAtivoFalse();

    // Verificar se CPF já existe (excluindo um ID específico)
    @Query("SELECT COUNT(c) > 0 FROM Cliente c WHERE c.cpf = :cpf AND c.id != :id")
    boolean existsByCpfAndIdNot(@Param("cpf") String cpf, @Param("id") Long id);

    // Verificar se RG já existe (excluindo um ID específico)
    @Query("SELECT COUNT(c) > 0 FROM Cliente c WHERE c.rg = :rg AND c.id != :id")
    boolean existsByRgAndIdNot(@Param("rg") String rg, @Param("id") Long id);

    // Verificar se email já existe (excluindo um ID específico)
    @Query("SELECT COUNT(c) > 0 FROM Cliente c WHERE c.email = :email AND c.id != :id")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("id") Long id);

    // Buscar clientes com rendimentos acima de um valor
    @Query("SELECT c FROM Cliente c WHERE c.rendimentos IS NOT NULL AND c.rendimentos != ''")
    List<Cliente> findWithRendimentos();

    // Buscar clientes com empregadores
    @Query("SELECT c FROM Cliente c WHERE c.empregadores IS NOT NULL AND c.empregadores != ''")
    List<Cliente> findWithEmpregadores();

    // Contar clientes ativos
    Long countByAtivoTrue();

    // Buscar clientes cadastrados em um período
    @Query("SELECT c FROM Cliente c WHERE c.dataCadastro BETWEEN :dataInicio AND :dataFim")
    List<Cliente> findByDataCadastroBetween(@Param("dataInicio") java.time.LocalDateTime dataInicio, 
                                           @Param("dataFim") java.time.LocalDateTime dataFim);
}