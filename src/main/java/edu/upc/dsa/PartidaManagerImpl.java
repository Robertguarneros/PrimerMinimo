package edu.upc.dsa;

import edu.upc.dsa.models.Partida;
import edu.upc.dsa.models.Usuario;
import edu.upc.dsa.models.Juego;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;

public class PartidaManagerImpl implements PartidaManager {
    private static PartidaManager instance;
    protected List<Partida> partidas;
    protected List<Usuario> usuarios;
    final static Logger logger = Logger.getLogger(PartidaManagerImpl.class);
    private List<Juego> juegosDisponibles;

    private PartidaManagerImpl(List<Juego> juegosDisponibles) {
        this.juegosDisponibles=juegosDisponibles;
        this.partidas = new LinkedList<>();
        this.usuarios = new LinkedList<>();
    }

    public static PartidaManager getInstance(List<Juego> juegosDisponibles) {
        if (instance == null) instance = new PartidaManagerImpl(juegosDisponibles);
        return instance;
    }
    @Override
    public Partida pasarDeNivel(int idUsuario, int puntos, int nivelViejo, int nivelNuevo, String fecha) {
        Usuario usuario = encontrarUsuarioPorId(idUsuario);

        if (usuario != null) {
            Partida partidaActiva = usuario.obtenerPartidaActiva();

            if (partidaActiva != null) {
                Juego juego = encontrarJuegoPorId(partidaActiva.getIdJuego());

                if (nivelNuevo == juego.getNumberOfLevels()) {
                    partidaActiva.setPuntos(partidaActiva.getPuntos() + 100);
                    partidaActiva.setNivelActual(nivelNuevo);
                    partidaActiva.setFechaString(fecha);
                    usuario.finalizarPartidaActiva();
                } else {
                    partidaActiva.setNivelActual(nivelNuevo);
                    partidaActiva.setFechaString(fecha);
                }

                return partidaActiva;
            } else {
                logger.warn("El usuario " + idUsuario + " no tiene una partida activa.");
                return null;
            }
        } else {
            logger.warn("Usuario no encontrado con ID: " + idUsuario);
            return null;
        }
    }

    @Override
    public boolean finPartida(int idUsuario) {
        // Verifica si el usuario tiene una partida activa
        // Si es así, finaliza la partida y retorna true, de lo contrario, retorna false
        Usuario usuario = encontrarUsuarioPorId(idUsuario);
        if (usuario != null) {
            Partida partidaActiva = usuario.obtenerPartidaActiva();

            if (partidaActiva != null) {
                // Se encontró una partida activa para el usuario, ahora se finaliza
                // Realizar las operaciones necesarias para finalizar la partida
                partidaActiva.setNivelActual(0); // Indicar que la partida ha finalizado

                // Agregar lógica para registrar la fecha u otros detalles relevantes

                usuario.finalizarPartidaActiva(); // Marcar que el usuario ya no tiene una partida activa
                return true; // Se finalizó con éxito la partida
            } else {
                // El usuario no tiene una partida activa
                logger.warn("El usuario " + idUsuario + " no tiene una partida activa.");
                return false; // No hay una partida activa para finalizar
            }
        } else {
            // No se encontró el usuario
            logger.warn("Usuario no encontrado con ID: " + idUsuario);
            return false; // No se puede finalizar la partida, el usuario no existe
        }
    }
    // Método para encontrar un usuario por ID
    private Usuario encontrarUsuarioPorId(int idUsuario) {
        for (Usuario usuario : usuarios) {
            if (usuario.getIdUsuario() == idUsuario) {
                return usuario;
            }
        }
        return null; // No se encontró el usuario
    }
    @Override
    public List<Partida> consultarPartidasUsuario(int idUsuario) {
        // Devuelve la lista de partidas de un usuario con el ID proporcionado
        List<Partida> partidasUsuario = new LinkedList<>();
        for (Partida partida : partidas) {
            if (partida.getIdUsuario() == idUsuario) {
                partidasUsuario.add(partida);
            }
        }
        return partidasUsuario;
    }

    @Override
    public List<Partida> obtenerPartidasJugadas(int idUsuario) {
        Usuario usuario = encontrarUsuarioPorId(idUsuario);

        if (usuario != null) {
            // Se encontró el usuario, se obtienen las partidas jugadas
            return usuario.obtenerPartidasJugadas();
        } else {
            // No se encontró el usuario
            logger.warn("Usuario no encontrado con ID: " + idUsuario);
            return new ArrayList<>(); // Retorna una lista vacía si no se encuentra el usuario
        }
    }

    @Override
    public List<Usuario> consultarPuntuacionUsuariosPorJuego(int idJuego) {
        // Lógica para obtener la puntuación de los usuarios por juego
        // Devuelve una lista de usuarios con su puntuación en el juego proporcionado
        List<Usuario> usuariosEnJuego = new ArrayList<>();
        Juego juego = encontrarJuegoPorId(idJuego);

        if (juego != null) {
            for (Usuario usuario : usuarios) {
                List<Partida> partidasEnJuego = obtenerPartidasJugadasEnJuego(usuario, idJuego);
                if (!partidasEnJuego.isEmpty()) {
                    // Calcular la puntuación más alta de las partidas jugadas por el usuario en el juego
                    int maxPuntuacion = calcularPuntuacionMasAlta(partidasEnJuego);
                    usuario.setPuntuacionMaxima(maxPuntuacion);
                    usuariosEnJuego.add(usuario);
                }
            }

            // Ordenar la lista de usuarios por puntuación (descendente)
            usuariosEnJuego.sort(Comparator.comparingInt(Usuario::getPuntuacionMaxima).reversed());
            return usuariosEnJuego;
        } else {
            // No se encontró el juego
            logger.warn("Juego no encontrado con ID: " + idJuego);
            return new ArrayList<>(); // Retorna una lista vacía si no se encuentra el juego
        }
    }
    private Juego encontrarJuegoPorId(int idJuego) {
        for (Juego juego : juegosDisponibles) { // Suponiendo que tienes una lista que almacena los juegos
            if (juego.getId().equals(idJuego)) {
                return juego;
            }
        }
        return null; // Si no se encuentra el juego, retornamos null
    }

    private List<Partida> obtenerPartidasJugadasEnJuego(Usuario usuario, int idJuego) {
        List<Partida> partidasEnJuego = new ArrayList<>();
        for (Partida partida : usuario.obtenerPartidasJugadas()) {
            if (partida.getIdJuego() == idJuego) {
                partidasEnJuego.add(partida);
            }
        }
        return partidasEnJuego;
    }

    private int calcularPuntuacionMasAlta(List<Partida> partidas) {
        int maxPuntuacion = Integer.MIN_VALUE;
        for (Partida partida : partidas) {
            if (partida.getPuntos() > maxPuntuacion) {
                maxPuntuacion = partida.getPuntos();
            }
        }
        return maxPuntuacion;
    }
}