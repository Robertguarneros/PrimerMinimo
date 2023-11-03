package edu.upc.dsa;

import edu.upc.dsa.models.Juego;

import java.util.List;

public interface JuegoManager {


    public Juego addJuego(String juegoID, String descripcion, int numberOfLevels);
    public Juego addJuego(Juego j);
    public Juego getJuego(String juegoID);
    public List<Juego> findAll();
    public void deleteJuego(String juegoID);
    public Juego updateJuego(Juego j);

    public int size();
}
