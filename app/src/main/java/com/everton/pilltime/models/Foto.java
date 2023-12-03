package com.everton.pilltime.models;


import com.everton.pilltime.alarme.Alarme;

public class Foto {

        private Long id;


        private byte[] arquivo;


        private Alarme alarme;


    public Foto(Long id, byte[] arquivo, Alarme alarme) {
        this.id = id;
        this.arquivo = arquivo;
        this.alarme = alarme;
    }

    public Foto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getArquivo() {
        return arquivo;
    }

    public void setArquivo(byte[] arquivo) {
        this.arquivo = arquivo;
    }

    public Alarme getAlarme() {
        return alarme;
    }

    public void setAlarme(Alarme alarme) {
        this.alarme = alarme;
    }
}


