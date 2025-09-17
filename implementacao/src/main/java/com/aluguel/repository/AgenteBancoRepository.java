package com.aluguel.repository;

import com.aluguel.model.AgenteBanco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AgenteBancoRepository extends JpaRepository<AgenteBanco, Long> {

    // Buscar por código do banco
    Optional<AgenteBanco> findByCodigoBanco(String codigoBanco);

    // Buscar por número da agência
    List<AgenteBanco> findByNumeroAgencia(String numeroAgencia);

    // Buscar por número da conta
    List<AgenteBanco> findByNumeroConta(String numeroConta);

    // Buscar bancos ativos para operações
    List<AgenteBanco> findByAtivoOperacoesTrue();

    // Buscar bancos inativos para operações
    List<AgenteBanco> findByAtivoOperacoesFalse();

    // Buscar por tipo de conta
    List<AgenteBanco> findByTipoConta(String tipoConta);

    // Buscar bancos com limite de crédito acima de um valor
    @Query("SELECT ab FROM AgenteBanco ab WHERE ab.limiteCredito >= :limiteMinimo")
    List<AgenteBanco> findByLimiteCreditoGreaterThanEqual(@Param("limiteMinimo") BigDecimal limiteMinimo);

    // Buscar bancos com taxa de juros abaixo de um valor
    @Query("SELECT ab FROM AgenteBanco ab WHERE ab.taxaJuros <= :taxaMaxima")
    List<AgenteBanco> findByTaxaJurosLessThanEqual(@Param("taxaMaxima") BigDecimal taxaMaxima);

    // Buscar bancos que podem operar com um valor específico
    @Query("SELECT ab FROM AgenteBanco ab WHERE ab.ativoOperacoes = true " +
           "AND (ab.valorMinimoOperacao IS NULL OR ab.valorMinimoOperacao <= :valor) " +
           "AND (ab.valorMaximoOperacao IS NULL OR ab.valorMaximoOperacao >= :valor)")
    List<AgenteBanco> findBancosQuePodemOperarComValor(@Param("valor") BigDecimal valor);

    // Buscar bancos com prazo máximo de pagamento acima de um valor
    @Query("SELECT ab FROM AgenteBanco ab WHERE ab.prazoMaximoPagamento >= :prazoMinimo")
    List<AgenteBanco> findByPrazoMaximoPagamentoGreaterThanEqual(@Param("prazoMinimo") Integer prazoMinimo);

    // Verificar se código do banco já existe (excluindo um ID específico)
    @Query("SELECT COUNT(ab) > 0 FROM AgenteBanco ab WHERE ab.codigoBanco = :codigoBanco AND ab.id != :id")
    boolean existsByCodigoBancoAndIdNot(@Param("codigoBanco") String codigoBanco, @Param("id") Long id);

    // Buscar bancos credenciados e ativos
    @Query("SELECT ab FROM AgenteBanco ab WHERE ab.credenciado = true AND ab.ativoOperacoes = true " +
           "AND ab.dataVencimentoCredenciamento > CURRENT_DATE")
    List<AgenteBanco> findBancosAtivosECredenciados();

    // Contar bancos ativos para operações
    Long countByAtivoOperacoesTrue();

    // Buscar bancos por faixa de taxa de juros
    @Query("SELECT ab FROM AgenteBanco ab WHERE ab.taxaJuros BETWEEN :taxaMinima AND :taxaMaxima")
    List<AgenteBanco> findByTaxaJurosBetween(@Param("taxaMinima") BigDecimal taxaMinima, 
                                           @Param("taxaMaxima") BigDecimal taxaMaxima);

    // Buscar bancos por faixa de limite de crédito
    @Query("SELECT ab FROM AgenteBanco ab WHERE ab.limiteCredito BETWEEN :limiteMinimo AND :limiteMaximo")
    List<AgenteBanco> findByLimiteCreditoBetween(@Param("limiteMinimo") BigDecimal limiteMinimo, 
                                                @Param("limiteMaximo") BigDecimal limiteMaximo);
}
