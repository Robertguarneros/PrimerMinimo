package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

public class Partida {
    String idPartida;
    int idJuego;
    int idUsuario;
    int puntos;
    int nivelActual;
    String fechaString;

    public String getFechaString() {
        return fechaString;
    }

    public void setFechaString(String fechaString) {
        this.fechaString = fechaString;
    }

    public Partida() {
        this.idPartida = RandomUtils.getId();
    }

    public Partida(int idJuego, int idUsuario, int puntos, int nivelActual) {
        this.idJuego = idJuego;
        this.idUsuario = idUsuario;
        this.puntos = 50;
        this.nivelActual = 1;//empieza en nivel 1 por defecto
    }

    public String getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(String idPartida) {
        this.idPartida = idPartida;
    }

    public int getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(int idJuego) {
        this.idJuego = idJuego;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getNivelActual() {
        return nivelActual;
    }

    public void setNivelActual(int nivelActual) {
        this.nivelActual = nivelActual;
    }


}
