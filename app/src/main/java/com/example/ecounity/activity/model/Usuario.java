package com.example.ecounity.activity.model;

public class Usuario {
    private String uid;
    private String nome;
    private String bio;
    private String data;
    private String cidade;
    private String estado;
    private String sexo;
    private String email;
    private String senha;
    private String fotoPerfil;

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    private String whatsapp;
    private Boolean statusConexao;

    public Usuario() {
        // Construtor vazio
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public Boolean getStatusConexao() {
        return statusConexao;
    }

    public void setStatusConexao(Boolean statusConexao) {
        this.statusConexao = statusConexao;
    }

    public String getFotoPerfilUrl() {
        return fotoPerfil;
    }

    public boolean isOnline() {
        return false;
    }


}
