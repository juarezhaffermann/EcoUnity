package com.example.ecounity.activity.model;

public class Practice {
    private String titulo;
    private String descricao;
    private String categoria;

    @SuppressWarnings("unused")
    public Practice() {
        // Construtor vazio
    }

    public Practice(String titulo, String descricao, String categoria) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.categoria = categoria;
    }

    // Getters
    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCategoria() {
        return categoria;
    }
}