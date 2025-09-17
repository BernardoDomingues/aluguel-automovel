package com.aluguel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_usuario", discriminatorType = DiscriminatorType.STRING)
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter um formato válido")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Column(nullable = false)
    private String senha;

    @NotBlank(message = "Telefone é obrigatório")
    @Column(nullable = false)
    private String telefone;

    @NotBlank(message = "Endereço é obrigatório")
    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro = LocalDateTime.now();

    @Column(name = "ultima_atualizacao")
    private LocalDateTime ultimaAtualizacao;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    // Construtores
    public Usuario() {}

    public Usuario(String nome, String email, String senha, String telefone, String endereco, String observacoes) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.endereco = endereco;
        this.observacoes = observacoes;
        this.ativo = true;
        this.dataCadastro = LocalDateTime.now();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public LocalDateTime getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(LocalDateTime ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    // Método abstrato para obter o tipo de usuário
    public abstract String getTipoUsuario();

    // Método para atualizar timestamp
    @PreUpdate
    public void preUpdate() {
        this.ultimaAtualizacao = LocalDateTime.now();
    }
}
