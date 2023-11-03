package edu.upc.dsa.models;

import java.util.ArrayList;
import java.util.List;

public class Usuario {//Falta cambiar el id a string y volver a verificar el codigo.
    String idUsuario;
    Partida partidaActiva;
    List<Partida> partidasJugadas;
    int puntuacionMaxima;


    public Usuario(String idUsuario) {
        this.idUsuario = idUsuario;
        this.puntuacionMaxima = 0;
        this.partidasJugadas = new ArrayList<>();
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
    public int getPuntuacionMaxima() {
        return puntuacionMaxima;
    }

    public void setPuntuacionMaxima(int puntuacionMaxima) {
        this.puntuacionMaxima = puntuacionMaxima;
    }

    // Métodos para manejar la partida activa
    public Partida obtenerPartidaActiva() {
        return partidaActiva;
    }

    public void asignarPartidaActiva(Partida partida) {
        if (partidaActiva == null) {
            this.partidaActiva = partida;
        } else {
            System.out.println("El usuario ya tiene una partida activa.");
            // Aquí podrías manejar cómo deseas que se comporte si ya hay una partida activa.
        }
    }

    public void finalizarPartidaActiva() {
        this.partidaActiva = null;
    }

    // Métodos para manejar las partidas jugadas
    public List<Partida> obtenerPartidasJugadas() {
        return partidasJugadas;
    }

    public void agregarPartidaJugada(Partida partida) {
        partidasJugadas.add(partida);
    }
    public void actualizarPuntuacionMaxima(List<Partida> partidasJugadas) {
        int maxPuntuacion = Integer.MIN_VALUE;
        for (Partida partida : partidasJugadas) {
            if (partida.getPuntos() > maxPuntuacion) {
                maxPuntuacion = partida.getPuntos();
            }
        }
        this.setPuntuacionMaxima(maxPuntuacion);
    }
}
