package com.aluguel.repository;

import com.aluguel.model.AgenteEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AgenteEmpresaRepository extends JpaRepository<AgenteEmpresa, Long> {

    // Buscar por segmento de atuação
    List<AgenteEmpresa> findBySegmentoAtuacao(String segmentoAtuacao);

    // Buscar por porte da empresa
    List<AgenteEmpresa> findByPorteEmpresa(String porteEmpresa);

    // Buscar por regime tributário
    List<AgenteEmpresa> findByRegimeTributario(String regimeTributario);

    // Buscar empresas ativas para operações
    List<AgenteEmpresa> findByAtivoOperacoesTrue();

    // Buscar empresas inativas para operações
    List<AgenteEmpresa> findByAtivoOperacoesFalse();

    // Buscar empresas com número de funcionários acima de um valor
    @Query("SELECT ae FROM AgenteEmpresa ae WHERE ae.numeroFuncionarios >= :numeroMinimo")
    List<AgenteEmpresa> findByNumeroFuncionariosGreaterThanEqual(@Param("numeroMinimo") Integer numeroMinimo);

    // Buscar empresas com faturamento anual acima de um valor
    @Query("SELECT ae FROM AgenteEmpresa ae WHERE ae.faturamentoAnual >= :faturamentoMinimo")
    List<AgenteEmpresa> findByFaturamentoAnualGreaterThanEqual(@Param("faturamentoMinimo") BigDecimal faturamentoMinimo);

    // Buscar empresas com capital social acima de um valor
    @Query("SELECT ae FROM AgenteEmpresa ae WHERE ae.capitalSocial >= :capitalMinimo")
    List<AgenteEmpresa> findByCapitalSocialGreaterThanEqual(@Param("capitalMinimo") BigDecimal capitalMinimo);

    // Buscar empresas fundadas após uma data
    List<AgenteEmpresa> findByDataFundacaoAfter(LocalDate data);

    // Buscar empresas fundadas antes de uma data
    List<AgenteEmpresa> findByDataFundacaoBefore(LocalDate data);

    // Buscar empresas com capacidade de atendimento acima de um valor
    @Query("SELECT ae FROM AgenteEmpresa ae WHERE ae.capacidadeAtendimento >= :capacidadeMinima")
    List<AgenteEmpresa> findByCapacidadeAtendimentoGreaterThanEqual(@Param("capacidadeMinima") Integer capacidadeMinima);

    // Buscar empresas com número de veículos na frota acima de um valor
    @Query("SELECT ae FROM AgenteEmpresa ae WHERE ae.numeroVeiculosFrota >= :numeroMinimo")
    List<AgenteEmpresa> findByNumeroVeiculosFrotaGreaterThanEqual(@Param("numeroMinimo") Integer numeroMinimo);

    // Buscar empresas que atendem uma região específica
    @Query("SELECT ae FROM AgenteEmpresa ae WHERE LOWER(ae.regioesAtendimento) LIKE LOWER(CONCAT('%', :regiao, '%'))")
    List<AgenteEmpresa> findByRegioesAtendimentoContaining(@Param("regiao") String regiao);

    // Buscar empresas que oferecem uma modalidade de serviço específica
    @Query("SELECT ae FROM AgenteEmpresa ae WHERE LOWER(ae.modalidadesServico) LIKE LOWER(CONCAT('%', :modalidade, '%'))")
    List<AgenteEmpresa> findByModalidadesServicoContaining(@Param("modalidade") String modalidade);

    // Buscar empresas credenciadas e ativas
    @Query("SELECT ae FROM AgenteEmpresa ae WHERE ae.credenciado = true AND ae.ativoOperacoes = true " +
           "AND ae.dataVencimentoCredenciamento > CURRENT_DATE")
    List<AgenteEmpresa> findEmpresasAtivasECredenciadas();

    // Buscar empresas de grande porte
    @Query("SELECT ae FROM AgenteEmpresa ae WHERE ae.porteEmpresa = 'GRANDE' " +
           "OR ae.numeroFuncionarios > 100 " +
           "OR ae.faturamentoAnual > 300000000")
    List<AgenteEmpresa> findEmpresasGrandePorte();

    // Buscar empresas por faixa de faturamento
    @Query("SELECT ae FROM AgenteEmpresa ae WHERE ae.faturamentoAnual BETWEEN :faturamentoMinimo AND :faturamentoMaximo")
    List<AgenteEmpresa> findByFaturamentoAnualBetween(@Param("faturamentoMinimo") BigDecimal faturamentoMinimo, 
                                                     @Param("faturamentoMaximo") BigDecimal faturamentoMaximo);

    // Buscar empresas por faixa de número de funcionários
    @Query("SELECT ae FROM AgenteEmpresa ae WHERE ae.numeroFuncionarios BETWEEN :numeroMinimo AND :numeroMaximo")
    List<AgenteEmpresa> findByNumeroFuncionariosBetween(@Param("numeroMinimo") Integer numeroMinimo, 
                                                       @Param("numeroMaximo") Integer numeroMaximo);

    // Contar empresas ativas para operações
    Long countByAtivoOperacoesTrue();

    // Contar empresas por segmento
    Long countBySegmentoAtuacao(String segmentoAtuacao);

    // Contar empresas por porte
    Long countByPorteEmpresa(String porteEmpresa);

    // Buscar empresas com certificações
    @Query("SELECT ae FROM AgenteEmpresa ae WHERE ae.certificacoes IS NOT NULL AND ae.certificacoes != ''")
    List<AgenteEmpresa> findWithCertificacoes();

    // Buscar empresas com licenças ambientais
    @Query("SELECT ae FROM AgenteEmpresa ae WHERE ae.licencasAmbientais IS NOT NULL AND ae.licencasAmbientais != ''")
    List<AgenteEmpresa> findWithLicencasAmbientais();
}
