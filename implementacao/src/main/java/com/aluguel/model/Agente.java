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

    @Column(name = "credenciado", nullable = false)
    private Boolean credenciado = false;

    public Agente() {
        super();
    }

    public Agente(String nome, String email, String senha, String endereco, String cnpj, String razaoSocial) {
        super(nome, email, senha, endereco);
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.credenciado = false;
    }
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

    public Boolean getCredenciado() {
        return credenciado;
    }

    public void setCredenciado(Boolean credenciado) {
        this.credenciado = credenciado;
    }

    @Override
    public String getTipoUsuario() {
        return "AGENTE";
    }
}
