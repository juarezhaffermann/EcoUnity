package com.example.ecounity.activity.model;


public class Evento {

    private String nome;
    private String data;
    private String horario;
    private String local;

    public Evento() {
        // Construtor vazio necessário para o Firestore
    }

    public Evento(String nome, String data, String horario, String local) {
        this.nome = nome;
        this.data = data;
        this.horario = horario;
        this.local = local;
    }





    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}