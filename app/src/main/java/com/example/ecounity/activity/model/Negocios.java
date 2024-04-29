package com.example.ecounity.activity.model;

import java.security.Timestamp;
import java.util.ArrayList;

public class Negocios {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getLogotipo() {
        return logotipo;
    }

    public void setLogotipo(String logotipo) {
        this.logotipo = logotipo;
    }

    public ArrayList<String> getImagens() {
        return imagens;
    }

    public void setImagens(ArrayList<String> imagens) {
        this.imagens = imagens;
    }

    public double getAvaliacaoMedia() {
        return avaliacaoMedia;
    }

    public void setAvaliacaoMedia(double avaliacaoMedia) {
        this.avaliacaoMedia = avaliacaoMedia;
    }

    public int getNumeroDeAvaliacoes() {
        return numeroDeAvaliacoes;
    }

    public void setNumeroDeAvaliacoes(int numeroDeAvaliacoes) {
        this.numeroDeAvaliacoes = numeroDeAvaliacoes;
    }

    public ArrayList<Object> getProdutosServicos() {
        return produtosServicos;
    }

    public void setProdutosServicos(ArrayList<Object> produtosServicos) {
        this.produtosServicos = produtosServicos;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Object getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Object localizacao) {
        this.localizacao = localizacao;
    }

    public Timestamp getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Timestamp criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Timestamp getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(Timestamp atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }

    private String id;
    private String nome;
    private String descricao;
    private String site;
    private String email;
    private String telefone;
    private String logotipo;
    private ArrayList<String> imagens;
    private double avaliacaoMedia;
    private int numeroDeAvaliacoes;
    private ArrayList<Object> produtosServicos;
    private String categoria;
    private Object localizacao;
    private Timestamp criadoEm;
    private Timestamp atualizadoEm;


}
