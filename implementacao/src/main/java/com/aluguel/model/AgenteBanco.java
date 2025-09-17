package com.aluguel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

@Entity
@Table(name = "agentes_banco")
@DiscriminatorValue("AGENTE_BANCO")
@PrimaryKeyJoinColumn(name = "agente_id")
public class AgenteBanco extends Agente {

    @NotBlank(message = "Código do banco é obrigatório")
    @Column(name = "codigo_banco", nullable = false, unique = true)
    private String codigoBanco;

    @NotBlank(message = "Número da agência é obrigatório")
    @Column(name = "numero_agencia", nullable = false)
    private String numeroAgencia;

    @Column(name = "digito_agencia")
    private String digitoAgencia;

    @NotBlank(message = "Número da conta é obrigatório")
    @Column(name = "numero_conta", nullable = false)
    private String numeroConta;

    @Column(name = "digito_conta")
    private String digitoConta;

    @Column(name = "tipo_conta")
    private String tipoConta; // CORRENTE, POUPANCA, etc.

    @Column(name = "limite_credito", precision = 15, scale = 2)
    private BigDecimal limiteCredito;

    @Column(name = "taxa_juros", precision = 5, scale = 4)
    private BigDecimal taxaJuros;

    @Column(name = "prazo_maximo_pagamento")
    private Integer prazoMaximoPagamento; // em dias

    @Column(name = "valor_minimo_operacao", precision = 10, scale = 2)
    private BigDecimal valorMinimoOperacao;

    @Column(name = "valor_maximo_operacao", precision = 15, scale = 2)
    private BigDecimal valorMaximoOperacao;

    @Column(name = "ativo_operacoes", nullable = false)
    private Boolean ativoOperacoes = true;

    // Construtores
    public AgenteBanco() {
        super();
    }

    public AgenteBanco(String nome, String email, String telefone, String endereco, String cnpj, 
                       String razaoSocial, String nomeFantasia, String responsavelLegal, 
                       String cpfResponsavel, String telefoneComercial, String emailComercial, 
                       String areaAtuacao, String codigoBanco, String numeroAgencia, 
                       String numeroConta, String observacoes) {
        super(nome, email, telefone, endereco, cnpj, razaoSocial, nomeFantasia, responsavelLegal, 
              cpfResponsavel, telefoneComercial, emailComercial, areaAtuacao, observacoes);
        this.codigoBanco = codigoBanco;
        this.numeroAgencia = numeroAgencia;
        this.numeroConta = numeroConta;
        this.ativoOperacoes = true;
    }

    // Getters e Setters específicos do AgenteBanco
    public String getCodigoBanco() {
        return codigoBanco;
    }

    public void setCodigoBanco(String codigoBanco) {
        this.codigoBanco = codigoBanco;
    }

    public String getNumeroAgencia() {
        return numeroAgencia;
    }

    public void setNumeroAgencia(String numeroAgencia) {
        this.numeroAgencia = numeroAgencia;
    }

    public String getDigitoAgencia() {
        return digitoAgencia;
    }

    public void setDigitoAgencia(String digitoAgencia) {
        this.digitoAgencia = digitoAgencia;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getDigitoConta() {
        return digitoConta;
    }

    public void setDigitoConta(String digitoConta) {
        this.digitoConta = digitoConta;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    public BigDecimal getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(BigDecimal limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public BigDecimal getTaxaJuros() {
        return taxaJuros;
    }

    public void setTaxaJuros(BigDecimal taxaJuros) {
        this.taxaJuros = taxaJuros;
    }

    public Integer getPrazoMaximoPagamento() {
        return prazoMaximoPagamento;
    }

    public void setPrazoMaximoPagamento(Integer prazoMaximoPagamento) {
        this.prazoMaximoPagamento = prazoMaximoPagamento;
    }

    public BigDecimal getValorMinimoOperacao() {
        return valorMinimoOperacao;
    }

    public void setValorMinimoOperacao(BigDecimal valorMinimoOperacao) {
        this.valorMinimoOperacao = valorMinimoOperacao;
    }

    public BigDecimal getValorMaximoOperacao() {
        return valorMaximoOperacao;
    }

    public void setValorMaximoOperacao(BigDecimal valorMaximoOperacao) {
        this.valorMaximoOperacao = valorMaximoOperacao;
    }

    public Boolean getAtivoOperacoes() {
        return ativoOperacoes;
    }

    public void setAtivoOperacoes(Boolean ativoOperacoes) {
        this.ativoOperacoes = ativoOperacoes;
    }

    @Override
    public String getTipoUsuario() {
        return "AGENTE_BANCO";
    }

    // Método para verificar se pode operar com um valor
    public boolean podeOperarComValor(BigDecimal valor) {
        if (!ativoOperacoes || !isCredenciamentoValido()) {
            return false;
        }
        
        if (valorMinimoOperacao != null && valor.compareTo(valorMinimoOperacao) < 0) {
            return false;
        }
        
        if (valorMaximoOperacao != null && valor.compareTo(valorMaximoOperacao) > 0) {
            return false;
        }
        
        return true;
    }

    // Método para calcular juros
    public BigDecimal calcularJuros(BigDecimal valor, Integer prazoDias) {
        if (taxaJuros == null || prazoMaximoPagamento == null || prazoDias > prazoMaximoPagamento) {
            return BigDecimal.ZERO;
        }
        
        // Cálculo simples de juros: valor * taxa * (prazo/365)
        BigDecimal taxaPeriodo = taxaJuros.multiply(BigDecimal.valueOf(prazoDias))
                                         .divide(BigDecimal.valueOf(365), 4, java.math.RoundingMode.HALF_UP);
        return valor.multiply(taxaPeriodo);
    }
}
