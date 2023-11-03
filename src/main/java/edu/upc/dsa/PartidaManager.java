package edu.upc.dsa;

import edu.upc.dsa.models.Partida;
import edu.upc.dsa.models.Usuario;

import java.util.List;

public interface PartidaManager {
    // Métodos para Partida
    Partida pasarDeNivel(int idUsuario, int puntos, int nivelViejo, int nivelNuevo, String fecha);
    boolean finPartida(int idUsuario);
    List<Partida> consultarPartidasUsuario(int idUsuario);

    // Métodos para Usuario
    List<Partida> obtenerPartidasJugadas(int idUsuario);
    List<Usuario> consultarPuntuacionUsuariosPorJuego(int idJuego);
}
