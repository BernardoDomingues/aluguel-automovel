package com.aluguel.dto;

import com.aluguel.model.Usuario;

public class LoginResponse {
    
    private String token;
    private Usuario usuario;
    private String tipoUsuario;
    private String mensagem;

    // Construtores
    public LoginResponse() {}

    public LoginResponse(String token, Usuario usuario, String tipoUsuario, String mensagem) {
        this.token = token;
        this.usuario = usuario;
        this.tipoUsuario = tipoUsuario;
        this.mensagem = mensagem;
    }

    // Getters e Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
