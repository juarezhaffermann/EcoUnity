package com.example.ecounity.activity.model;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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

    public long getBeginTimeInMillis() {
        return convertToMillis(data, horario);
    }

    public long getEndTimeInMillis() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(convertToMillis(data, horario));
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        return calendar.getTimeInMillis();
    }

    private long convertToMillis(String data, String horario) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        try {
            return dateFormat.parse(data + " " + horario).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return -1; // Valor inválido
        }
    }
}