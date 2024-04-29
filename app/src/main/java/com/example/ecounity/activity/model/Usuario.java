package com.example.ecounity.activity.model;

import com.google.firebase.firestore.QuerySnapshot;

public class Usuario {

    private String uid;

    public String getNome() {
        return nome;
    }

    private String nome;
    private String email;
    private String senha;
    private String fotoPerfil;
    private Boolean statusConexao;

    public Usuario() {

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

    public QuerySnapshot getFotoPerfilUrl() {
        return null;
    }

    public boolean isOnline() {
        return false;
    }
}
