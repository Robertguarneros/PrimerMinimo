package edu.upc.dsa.models;

public class Juego {
    String juegoID;
    String descripcion;
    int numberOfLevels;

    public Juego(String juegoID, String descripcion, int numberOfLevels) {
        this.juegoID = juegoID;
        this.descripcion = descripcion;
        this.numberOfLevels = numberOfLevels;
    }
    public String getId() {
        return this.juegoID;
    }

    public void setId(String juegoID) {
        this.juegoID=juegoID;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNumberOfLevels() {
        return numberOfLevels;
    }

    public void setNumberOfLevels(int numberOfLevels) {
        this.numberOfLevels = numberOfLevels;
    }
    @Override
    public String toString() {
        return "Juego [ID="+juegoID+", Descripcion=" + descripcion + ", Numero de Niveles=" + numberOfLevels +"]";
    }
}
