package com.aluguel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

@Entity
@Table(name = "automoveis")
public class Automovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Matrícula é obrigatória")
    @Column(nullable = false, unique = true)
    private String matricula;

    @NotNull(message = "Ano é obrigatório")
    @Positive(message = "Ano deve ser positivo")
    @Column(nullable = false)
    private Integer ano;

    @NotBlank(message = "Marca é obrigatória")
    @Column(nullable = false)
    private String marca;

    @NotBlank(message = "Modelo é obrigatório")
    @Column(nullable = false)
    private String modelo;

    @NotBlank(message = "Placa é obrigatória")
    @Pattern(regexp = "[A-Z]{3}[0-9]{4}|[A-Z]{3}[0-9][A-Z][0-9]{2}", message = "Placa deve estar no formato ABC1234 ou ABC1A23")
    @Column(nullable = false, unique = true)
    private String placa;

    @NotNull(message = "Valor do aluguel é obrigatório")
    @Positive(message = "Valor do aluguel deve ser positivo")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorAluguel;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false)
    private Boolean disponivel = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoProprietario proprietario = TipoProprietario.EMPRESA;

    public enum TipoProprietario {
        CLIENTE, EMPRESA, BANCO
    }

    // Construtores
    public Automovel() {}

    public Automovel(Long id, String matricula, Integer ano, String marca, String modelo, String placa, 
                    BigDecimal valorAluguel, String descricao, Boolean disponivel, TipoProprietario proprietario) {
        this.id = id;
        this.matricula = matricula;
        this.ano = ano;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.valorAluguel = valorAluguel;
        this.descricao = descricao;
        this.disponivel = disponivel;
        this.proprietario = proprietario;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public BigDecimal getValorAluguel() {
        return valorAluguel;
    }

    public void setValorAluguel(BigDecimal valorAluguel) {
        this.valorAluguel = valorAluguel;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }

    public TipoProprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(TipoProprietario proprietario) {
        this.proprietario = proprietario;
    }
}
