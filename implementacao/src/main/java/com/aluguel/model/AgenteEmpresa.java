package com.aluguel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "agentes_empresa")
@DiscriminatorValue("AGENTE_EMPRESA")
@PrimaryKeyJoinColumn(name = "agente_id")
public class AgenteEmpresa extends Agente {

    @NotBlank(message = "Segmento de atuação é obrigatório")
    @Column(name = "segmento_atuacao", nullable = false)
    private String segmentoAtuacao;

    public AgenteEmpresa() {
        super();
    }

    public AgenteEmpresa(String nome, String email, String senha, String endereco, String cnpj, 
                         String razaoSocial, String segmentoAtuacao) {
        super(nome, email, senha, endereco, cnpj, razaoSocial);
        this.segmentoAtuacao = segmentoAtuacao;
    }
    public String getSegmentoAtuacao() {
        return segmentoAtuacao;
    }

    public void setSegmentoAtuacao(String segmentoAtuacao) {
        this.segmentoAtuacao = segmentoAtuacao;
    }

    @Override
    public String getTipoUsuario() {
        return "AGENTE_EMPRESA";
    }
}
