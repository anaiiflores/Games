package com.example.games;

public class Casilla {
    private int valor;
    private boolean ocupada;

    public Casilla() {
        this.valor = 0;
        this.ocupada = false;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }
}

