package com.aluguel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "agentes_banco")
@DiscriminatorValue("AGENTE_BANCO")
@PrimaryKeyJoinColumn(name = "agente_id")
public class AgenteBanco extends Agente {

    @NotBlank(message = "Código do banco é obrigatório")
    @Column(name = "codigo_banco", nullable = false, unique = true)
    private String codigoBanco;

    public AgenteBanco() {
        super();
    }

    public AgenteBanco(String nome, String email, String senha, String endereco, String cnpj, 
                       String razaoSocial, String codigoBanco) {
        super(nome, email, senha, endereco, cnpj, razaoSocial);
        this.codigoBanco = codigoBanco;
    }
    public String getCodigoBanco() {
        return codigoBanco;
    }

    public void setCodigoBanco(String codigoBanco) {
        this.codigoBanco = codigoBanco;
    }

    @Override
    public String getTipoUsuario() {
        return "AGENTE_BANCO";
    }
}
