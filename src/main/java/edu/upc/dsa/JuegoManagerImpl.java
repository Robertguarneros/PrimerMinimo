package edu.upc.dsa;

import edu.upc.dsa.models.Juego;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class JuegoManagerImpl implements JuegoManager {
    private static JuegoManager instance;
    protected List<Juego> juegos;
    final static Logger logger = Logger.getLogger(JuegoManagerImpl.class);

    private JuegoManagerImpl() {
        this.juegos = new LinkedList<>();
    }

    public static JuegoManager getInstance() {
        if (instance==null) instance = new JuegoManagerImpl();
        return instance;
    }

    public int size() {
        int ret = this.juegos.size();
        logger.info("size " + ret);

        return ret;
    }

    public Juego addJuego(Juego j) {
        logger.info("Nuevo Juego " + j);

        this.juegos.add (j);
        logger.info("Nuevo Juego añadido");
        return j;
    }

    public Juego addJuego(String juegoID, String descripcion, int numberOfLevels) {
        return this.addJuego(new Juego(juegoID,descripcion,numberOfLevels));
    }

    public Juego getJuego(String juegoID) {
        logger.info("getTrack("+juegoID+")");

        for (Juego j: this.juegos) {
            if (j.getId().equals(juegoID)) {
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
