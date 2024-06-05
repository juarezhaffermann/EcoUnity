package com.example.ecounity.activity.model;

import java.util.ArrayList;

public class Negocios {
    private String id;
    private String nome;
    private String descricao;
    private String site;
    private String email;
    private String endereco;
    private String telefone;
    private String logotipo;
    private ArrayList<String> imagens;
    private double avaliacao_media;
    private int numero_avaliacoes;
    private String produto;
    private String descricao_produto;
    private double preco;

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

       public String getEndereco() { return endereco;}

    public void setEndereco(String endereco) {this.endereco = endereco;}



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

    public double getAvaliacao_media() {
        return avaliacao_media;
    }

    public void setAvaliacao_media(double avaliacao_media) {
        this.avaliacao_media = avaliacao_media;
    }

    public int getNumero_avaliacoes() {
        return numero_avaliacoes;
    }

    public void setNumero_avaliacoes(int numero_avaliacoes) {
        this.numero_avaliacoes = numero_avaliacoes;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getDescricao_produto() {
        return descricao_produto;
    }

    public void setDescricao_produto(String descricao_produto) {
        this.descricao_produto = descricao_produto;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
