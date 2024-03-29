package com.example.anotaesoficial.model;

import java.io.Serializable;

public class Anotacoes implements Serializable {

    private String titulo;
    private String campoText;
    private String data;
    private String ultimaAtualizacao;
    private Long id;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getcampoText() {
        return campoText;
    }

    public void setcampoText(String campoText) {
        this.campoText = campoText;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCampoText() {
        return campoText;
    }

    public void setCampoText(String campoText) {
        this.campoText = campoText;
    }

    public String getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(String ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
