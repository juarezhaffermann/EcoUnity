package com.example.ecounity.activity.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Projeto implements Serializable  {
    private String id;
    private String titulo;
    private String descricao;
    private String metas;
    private String criadorId;
    private String nome;
    private ByteArrayOutputStream byteArrayOutputStream;


    public Projeto() {

    }

    public Projeto(String id, String titulo, String descricao, String metas, String criadorId) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.metas = metas;
        this.criadorId = criadorId;
    }


    public byte[] toByteArray() throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(baos)) {
            objectOutputStream.writeObject(this);
            return baos.toByteArray();
        }
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMetas() {
        return metas;
    }

    public void setMetas(String metas) {
        this.metas = metas;
    }

    public String getCriadorId() {
        return criadorId;
    }

    public void setCriadorId(String criadorId) {
        this.criadorId = criadorId;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}

