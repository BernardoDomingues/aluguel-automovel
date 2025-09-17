package com.aluguel.repository;

import com.aluguel.model.Agente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AgenteRepository extends JpaRepository<Agente, Long> {

    // Buscar por CNPJ
    Optional<Agente> findByCnpj(String cnpj);

    // Buscar por razão social (contém)
    List<Agente> findByRazaoSocialContainingIgnoreCase(String razaoSocial);

    // Buscar por nome fantasia (contém)
    List<Agente> findByNomeFantasiaContainingIgnoreCase(String nomeFantasia);

    // Buscar por responsável legal
    List<Agente> findByResponsavelLegalContainingIgnoreCase(String responsavelLegal);

    // Buscar por CPF do responsável
    Optional<Agente> findByCpfResponsavel(String cpfResponsavel);

    // Buscar agentes credenciados
    List<Agente> findByCredenciadoTrue();

    // Buscar agentes não credenciados
    List<Agente> findByCredenciadoFalse();

    // Buscar agentes com credenciamento válido
    @Query("SELECT a FROM Agente a WHERE a.credenciado = true AND a.dataVencimentoCredenciamento > :dataAtual")
    List<Agente> findWithCredenciamentoValido(@Param("dataAtual") LocalDate dataAtual);

    // Buscar agentes com credenciamento vencido
    @Query("SELECT a FROM Agente a WHERE a.credenciado = true AND a.dataVencimentoCredenciamento <= :dataAtual")
    List<Agente> findWithCredenciamentoVencido(@Param("dataAtual") LocalDate dataAtual);

    // Buscar por área de atuação (contém)
    List<Agente> findByAreaAtuacaoContainingIgnoreCase(String areaAtuacao);

    // Buscar por email comercial
    Optional<Agente> findByEmailComercial(String emailComercial);

    // Buscar por telefone comercial
    Optional<Agente> findByTelefoneComercial(String telefoneComercial);

    // Verificar se CNPJ já existe (excluindo um ID específico)
    @Query("SELECT COUNT(a) > 0 FROM Agente a WHERE a.cnpj = :cnpj AND a.id != :id")
    boolean existsByCnpjAndIdNot(@Param("cnpj") String cnpj, @Param("id") Long id);

    // Verificar se CPF do responsável já existe (excluindo um ID específico)
    @Query("SELECT COUNT(a) > 0 FROM Agente a WHERE a.cpfResponsavel = :cpfResponsavel AND a.id != :id")
    boolean existsByCpfResponsavelAndIdNot(@Param("cpfResponsavel") String cpfResponsavel, @Param("id") Long id);

    // Buscar agentes cadastrados em um período
    @Query("SELECT a FROM Agente a WHERE a.dataCadastro BETWEEN :dataInicio AND :dataFim")
    List<Agente> findByDataCadastroBetween(@Param("dataInicio") java.time.LocalDateTime dataInicio, 
                                          @Param("dataFim") java.time.LocalDateTime dataFim);

    // Buscar agentes credenciados em um período
    @Query("SELECT a FROM Agente a WHERE a.dataCredenciamento BETWEEN :dataInicio AND :dataFim")
    List<Agente> findByDataCredenciamentoBetween(@Param("dataInicio") LocalDate dataInicio, 
                                                @Param("dataFim") LocalDate dataFim);

    // Contar agentes credenciados
    Long countByCredenciadoTrue();

    // Contar agentes ativos
    Long countByAtivoTrue();

    // Buscar agentes por tipo (usando discriminator)
    @Query("SELECT a FROM Agente a WHERE TYPE(a) = :tipoAgente")
    List<Agente> findByTipoAgente(@Param("tipoAgente") Class<? extends Agente> tipoAgente);
}
