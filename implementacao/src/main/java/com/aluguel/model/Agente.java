package com.aluguel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "agentes")
@DiscriminatorValue("AGENTE")
@PrimaryKeyJoinColumn(name = "usuario_id")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_agente", discriminatorType = DiscriminatorType.STRING)
public abstract class Agente extends Usuario {

    @NotBlank(message = "CNPJ é obrigatório")
    @Column(nullable = false, unique = true)
    private String cnpj;

    @NotBlank(message = "Razão social é obrigatória")
    @Column(nullable = false)
    private String razaoSocial;

    @Column(name = "nome_fantasia")
    private String nomeFantasia;

    @Column(name = "inscricao_estadual")
    private String inscricaoEstadual;

    @Column(name = "inscricao_municipal")
    private String inscricaoMunicipal;

    @Column(name = "responsavel_legal")
    private String responsavelLegal;

    @Column(name = "cpf_responsavel")
    private String cpfResponsavel;

    @Column(name = "telefone_comercial")
    private String telefoneComercial;

    @Column(name = "email_comercial")
    private String emailComercial;

    @Column(name = "site")
    private String site;

    @Column(name = "area_atuacao", columnDefinition = "TEXT")
    private String areaAtuacao;

    @Column(name = "credenciado", nullable = false)
    private Boolean credenciado = false;

    @Column(name = "data_credenciamento")
    private java.time.LocalDate dataCredenciamento;

    @Column(name = "data_vencimento_credenciamento")
    private java.time.LocalDate dataVencimentoCredenciamento;

    // Construtores
    public Agente() {
        super();
    }

    public Agente(String nome, String email, String senha, String telefone, String endereco, String cnpj, 
                  String razaoSocial, String nomeFantasia, String responsavelLegal, 
                  String cpfResponsavel, String telefoneComercial, String emailComercial, 
                  String areaAtuacao, String observacoes) {
        super(nome, email, senha, telefone, endereco, observacoes);
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.responsavelLegal = responsavelLegal;
        this.cpfResponsavel = cpfResponsavel;
        this.telefoneComercial = telefoneComercial;
        this.emailComercial = emailComercial;
        this.areaAtuacao = areaAtuacao;
        this.credenciado = false;
    }

    // Getters e Setters específicos do Agente
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getInscricaoMunicipal() {
        return inscricaoMunicipal;
    }

    public void setInscricaoMunicipal(String inscricaoMunicipal) {
        this.inscricaoMunicipal = inscricaoMunicipal;
    }

    public String getResponsavelLegal() {
        return responsavelLegal;
    }

    public void setResponsavelLegal(String responsavelLegal) {
        this.responsavelLegal = responsavelLegal;
    }

    public String getCpfResponsavel() {
        return cpfResponsavel;
    }

    public void setCpfResponsavel(String cpfResponsavel) {
        this.cpfResponsavel = cpfResponsavel;
    }

    public String getTelefoneComercial() {
        return telefoneComercial;
    }

    public void setTelefoneComercial(String telefoneComercial) {
        this.telefoneComercial = telefoneComercial;
    }

    public String getEmailComercial() {
        return emailComercial;
    }

    public void setEmailComercial(String emailComercial) {
        this.emailComercial = emailComercial;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getAreaAtuacao() {
        return areaAtuacao;
    }

    public void setAreaAtuacao(String areaAtuacao) {
        this.areaAtuacao = areaAtuacao;
    }

    public Boolean getCredenciado() {
        return credenciado;
    }

    public void setCredenciado(Boolean credenciado) {
        this.credenciado = credenciado;
    }

    public java.time.LocalDate getDataCredenciamento() {
        return dataCredenciamento;
    }

    public void setDataCredenciamento(java.time.LocalDate dataCredenciamento) {
        this.dataCredenciamento = dataCredenciamento;
    }

    public java.time.LocalDate getDataVencimentoCredenciamento() {
        return dataVencimentoCredenciamento;
    }

    public void setDataVencimentoCredenciamento(java.time.LocalDate dataVencimentoCredenciamento) {
        this.dataVencimentoCredenciamento = dataVencimentoCredenciamento;
    }

    @Override
    public String getTipoUsuario() {
        return "AGENTE";
    }

    // Método para credenciar o agente
    public void credenciar(java.time.LocalDate dataVencimento) {
        this.credenciado = true;
        this.dataCredenciamento = java.time.LocalDate.now();
        this.dataVencimentoCredenciamento = dataVencimento;
    }

    // Método para verificar se o credenciamento está válido
    public boolean isCredenciamentoValido() {
        if (!credenciado || dataVencimentoCredenciamento == null) {
            return false;
        }
        return java.time.LocalDate.now().isBefore(dataVencimentoCredenciamento);
    }
}
