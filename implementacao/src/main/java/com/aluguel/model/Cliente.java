package com.aluguel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "clientes")
@DiscriminatorValue("CLIENTE")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Cliente extends Usuario {

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}", message = "CPF deve estar no formato 000.000.000-00")
    @Column(nullable = false, unique = true)
    private String cpf;

    @NotBlank(message = "RG é obrigatório")
    @Column(nullable = false)
    private String rg;

    @NotBlank(message = "Profissão é obrigatória")
    @Column(nullable = false)
    private String profissao;

    @Column(columnDefinition = "TEXT")
    private String empregadores;

    @Column(columnDefinition = "TEXT")
    private String rendimentos;

    public Cliente() {
        super();
    }

    public Cliente(String nome, String email, String senha, String endereco, String cpf,
                  String rg, String profissao, String empregadores, String rendimentos) {
        super(nome, email, senha, endereco);
        this.cpf = cpf;
        this.rg = rg;
        this.profissao = profissao;
        this.empregadores = empregadores;
        this.rendimentos = rendimentos;
    }
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getEmpregadores() {
        return empregadores;
    }

    public void setEmpregadores(String empregadores) {
        this.empregadores = empregadores;
    }

    public String getRendimentos() {
        return rendimentos;
    }

    public void setRendimentos(String rendimentos) {
        this.rendimentos = rendimentos;
    }

    @Override
    public String getTipoUsuario() {
        return "CLIENTE";
    }
}
