package edu.upc.dsa;

import edu.upc.dsa.models.Usuario;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class UsuarioManagerImpl implements UsuarioManager {
    private static UsuarioManager instance;
    protected List<Usuario> usuarios;
    final static Logger logger = Logger.getLogger(UsuarioManagerImpl.class);

    private UsuarioManagerImpl() {
        this.usuarios = new LinkedList<>();
    }

    public static UsuarioManager getInstance() {
        if (instance==null) instance = new UsuarioManagerImpl();
        return instance;
    }

    public int size() {
        int ret = this.usuarios.size();
        logger.info("size " + ret);

        return ret;
    }

    public Usuario addUsuario(Usuario u) {
        logger.info("Nuevo Usuario " + u);

        this.usuarios.add (u);
        logger.info("Nuevo usuario añadido");
        return u;
    }

    public Usuario addUsuario(String idUsuario) {
        return this.addUsuario(new Usuario(idUsuario));
    }

    public Usuario getUsuario(String idUsuario) {
        logger.info("getUsuario("+idUsuario+")");

        for (Usuario u: this.usuarios) {
            if (u.getIdUsuario().equals(idUsuario)) {
                logger.info("getTrack("+juegoID+"): "+j);

                return j;
            }
        }

        logger.warn("not found " + juegoID);
        return null;
    }

    public List<Juego> findAll() {
        return this.juegos;
    }

    @Override
    public void deleteJuego(String juegoID) {

        Juego j = this.getJuego(juegoID);
        if (j==null) {
            logger.warn("not found " + j);
        }
        else logger.info(j+" deleted ");

        this.juegos.remove(j);

    }

    @Override
    public Juego updateJuego(Juego j) {
        Juego t = this.getJuego(j.getId());

        if (t!=null) {
            logger.info(j+" rebut!!!! ");

            t.setId(j.getId());
            t.setDescripcion(j.getDescripcion());
            t.setNumberOfLevels(j.getNumberOfLevels());

            logger.info(t+" updated ");
        }
        else {
            logger.warn("not found "+j);
        }

        return t;
    }
}
