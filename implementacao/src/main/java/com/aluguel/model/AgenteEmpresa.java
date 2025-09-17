package com.aluguel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

@Entity
@Table(name = "agentes_empresa")
@DiscriminatorValue("AGENTE_EMPRESA")
@PrimaryKeyJoinColumn(name = "agente_id")
public class AgenteEmpresa extends Agente {

    @NotBlank(message = "Segmento de atuação é obrigatório")
    @Column(name = "segmento_atuacao", nullable = false)
    private String segmentoAtuacao; // LOCADORA, CONCESSIONARIA, DISTRIBUIDORA, etc.

    @Column(name = "numero_funcionarios")
    private Integer numeroFuncionarios;

    @Column(name = "faturamento_anual", precision = 15, scale = 2)
    private BigDecimal faturamentoAnual;

    @Column(name = "capital_social", precision = 15, scale = 2)
    private BigDecimal capitalSocial;

    @Column(name = "data_fundacao")
    private java.time.LocalDate dataFundacao;

    @Column(name = "porte_empresa")
    private String porteEmpresa; // MICRO, PEQUENA, MEDIA, GRANDE

    @Column(name = "regime_tributario")
    private String regimeTributario; // SIMPLES, LUCRO_PRESUMIDO, LUCRO_REAL

    @Column(name = "certificacoes", columnDefinition = "TEXT")
    private String certificacoes; // ISO, etc.

    @Column(name = "licencas_ambientais", columnDefinition = "TEXT")
    private String licencasAmbientais;

    @Column(name = "numero_veiculos_frota")
    private Integer numeroVeiculosFrota;

    @Column(name = "capacidade_atendimento")
    private Integer capacidadeAtendimento; // clientes por mês

    @Column(name = "regioes_atendimento", columnDefinition = "TEXT")
    private String regioesAtendimento;

    @Column(name = "modalidades_servico", columnDefinition = "TEXT")
    private String modalidadesServico; // ALUGUEL_DIARIO, MENSAL, ANUAL, etc.

    @Column(name = "politica_cancelamento", columnDefinition = "TEXT")
    private String politicaCancelamento;

    @Column(name = "garantias_oferecidas", columnDefinition = "TEXT")
    private String garantiasOferecidas;

    @Column(name = "ativo_operacoes", nullable = false)
    private Boolean ativoOperacoes = true;

    // Construtores
    public AgenteEmpresa() {
        super();
    }

    public AgenteEmpresa(String nome, String email, String telefone, String endereco, String cnpj, 
                         String razaoSocial, String nomeFantasia, String responsavelLegal, 
                         String cpfResponsavel, String telefoneComercial, String emailComercial, 
                         String areaAtuacao, String segmentoAtuacao, String observacoes) {
        super(nome, email, telefone, endereco, cnpj, razaoSocial, nomeFantasia, responsavelLegal, 
              cpfResponsavel, telefoneComercial, emailComercial, areaAtuacao, observacoes);
        this.segmentoAtuacao = segmentoAtuacao;
        this.ativoOperacoes = true;
    }

    // Getters e Setters específicos do AgenteEmpresa
    public String getSegmentoAtuacao() {
        return segmentoAtuacao;
    }

    public void setSegmentoAtuacao(String segmentoAtuacao) {
        this.segmentoAtuacao = segmentoAtuacao;
    }

    public Integer getNumeroFuncionarios() {
        return numeroFuncionarios;
    }

    public void setNumeroFuncionarios(Integer numeroFuncionarios) {
        this.numeroFuncionarios = numeroFuncionarios;
    }

    public BigDecimal getFaturamentoAnual() {
        return faturamentoAnual;
    }

    public void setFaturamentoAnual(BigDecimal faturamentoAnual) {
        this.faturamentoAnual = faturamentoAnual;
    }

    public BigDecimal getCapitalSocial() {
        return capitalSocial;
    }

    public void setCapitalSocial(BigDecimal capitalSocial) {
        this.capitalSocial = capitalSocial;
    }

    public java.time.LocalDate getDataFundacao() {
        return dataFundacao;
    }

    public void setDataFundacao(java.time.LocalDate dataFundacao) {
        this.dataFundacao = dataFundacao;
    }

    public String getPorteEmpresa() {
        return porteEmpresa;
    }

    public void setPorteEmpresa(String porteEmpresa) {
        this.porteEmpresa = porteEmpresa;
    }

    public String getRegimeTributario() {
        return regimeTributario;
    }

    public void setRegimeTributario(String regimeTributario) {
        this.regimeTributario = regimeTributario;
    }

    public String getCertificacoes() {
        return certificacoes;
    }

    public void setCertificacoes(String certificacoes) {
        this.certificacoes = certificacoes;
    }

    public String getLicencasAmbientais() {
        return licencasAmbientais;
    }

    public void setLicencasAmbientais(String licencasAmbientais) {
        this.licencasAmbientais = licencasAmbientais;
    }

    public Integer getNumeroVeiculosFrota() {
        return numeroVeiculosFrota;
    }

    public void setNumeroVeiculosFrota(Integer numeroVeiculosFrota) {
        this.numeroVeiculosFrota = numeroVeiculosFrota;
    }

    public Integer getCapacidadeAtendimento() {
        return capacidadeAtendimento;
    }

    public void setCapacidadeAtendimento(Integer capacidadeAtendimento) {
        this.capacidadeAtendimento = capacidadeAtendimento;
    }

    public String getRegioesAtendimento() {
        return regioesAtendimento;
    }

    public void setRegioesAtendimento(String regioesAtendimento) {
        this.regioesAtendimento = regioesAtendimento;
    }

    public String getModalidadesServico() {
        return modalidadesServico;
    }

    public void setModalidadesServico(String modalidadesServico) {
        this.modalidadesServico = modalidadesServico;
    }

    public String getPoliticaCancelamento() {
        return politicaCancelamento;
    }

    public void setPoliticaCancelamento(String politicaCancelamento) {
        this.politicaCancelamento = politicaCancelamento;
    }

    public String getGarantiasOferecidas() {
        return garantiasOferecidas;
    }

    public void setGarantiasOferecidas(String garantiasOferecidas) {
        this.garantiasOferecidas = garantiasOferecidas;
    }

    public Boolean getAtivoOperacoes() {
        return ativoOperacoes;
    }

    public void setAtivoOperacoes(Boolean ativoOperacoes) {
        this.ativoOperacoes = ativoOperacoes;
    }

    @Override
    public String getTipoUsuario() {
        return "AGENTE_EMPRESA";
    }

    // Método para verificar se a empresa pode atender uma demanda
    public boolean podeAtenderDemanda(Integer numeroVeiculos, String regiao) {
        if (!ativoOperacoes || !isCredenciamentoValido()) {
            return false;
        }
        
        if (capacidadeAtendimento != null && numeroVeiculos > capacidadeAtendimento) {
            return false;
        }
        
        if (regioesAtendimento != null && !regioesAtendimento.toLowerCase().contains(regiao.toLowerCase())) {
            return false;
        }
        
        return true;
    }

    // Método para calcular capacidade de atendimento
    public double calcularCapacidadeUtilizacao(Integer veiculosEmUso) {
        if (capacidadeAtendimento == null || capacidadeAtendimento == 0) {
            return 0.0;
        }
        return (double) veiculosEmUso / capacidadeAtendimento * 100;
    }

    // Método para verificar se é empresa de grande porte
    public boolean isGrandePorte() {
        return "GRANDE".equalsIgnoreCase(porteEmpresa) || 
               (numeroFuncionarios != null && numeroFuncionarios > 100) ||
               (faturamentoAnual != null && faturamentoAnual.compareTo(new BigDecimal("300000000")) > 0);
    }
}
