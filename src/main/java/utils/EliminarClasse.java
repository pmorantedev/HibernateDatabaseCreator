package utils;

import entitats.Aeronau;
import entitats.Combat;
import entitats.Mecanic;
import entitats.Missio;
import entitats.Pilot;
import entitats.Pilotada;
import entitats.Soldat;
import java.util.List;
import main.SingleSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.hibernate.Hibernate.isInstance;
import org.hibernate.JDBCException;

/**
 *
 * @author pablomorante
 */
public class EliminarClasse {

    private static final Logger logger = LogManager.getLogger(LlistatMenuClasses.class);

    /**
     * *
     * Mètode que elimina un o més objectes de tipus Aeronau i els objectes que
     * té associats.
     *
     * @param singleton
     * @param idInicial
     * @param idFinal
     * @param tipus
     * @author Víctor García
     * @author Pablo Morante
     */
    public static void eliminarAeronau(SingleSession singleton, int idInicial, int idFinal, Class<?> tipus) {
        singleton.getSessioUsuari().beginTransaction();
        for (int i = idInicial; i <= idFinal; i++) {
            Aeronau combat = singleton.getSessioUsuari().get(Aeronau.class, i);
            if (combat != null && isInstance(combat, tipus)) {
                try {
                    singleton.getSessioUsuari().remove(combat);
                    singleton.getSessioUsuari().flush();
                    List<Missio> missions = combat.getMissions();
                    logger.info("- S'han eliminat correctament els següents registres i els seus items associats: \n" + "   " + combat.toString() + "\n          · Missions associades: " + missions + "\n");
                    if (combat instanceof Pilotada) {
                        Pilotada pilotada = (Pilotada) combat;
                        Pilot pilot = pilotada.getPilotAeronau();
                        List<Mecanic> mecanics = pilotada.getMecanics();
                        logger.info("          · Pilot associat: " + pilot + "\n          · Mecànics associats: " + mecanics + "\n");
                    }
                } catch (JDBCException ex) {
                    singleton.getSessioUsuari().getTransaction().rollback();
                }
            } else {
                logger.info("- No existeix cap registre amb aquest identificador -> " + i + "\n");
            }
        }
        singleton.getSessioUsuari().getTransaction().commit();
    }

    /**
     * *
     * Mètode que elimina un o més objectes de tipus Soldat i els objectes que
     * té associats.
     *
     * @param singleton
     * @param idInicial
     * @param idFinal
     * @param tipus
     * @author Víctor García
     * @author Pablo Morante
     */
    public static void eliminarSoldat(SingleSession singleton, int idInicial, int idFinal, Class<?> tipus) {
        singleton.getSessioUsuari().beginTransaction();
        for (int i = idInicial; i <= idFinal; i++) {
            Soldat soldat = singleton.getSessioUsuari().get(Soldat.class, i);
            if (soldat != null && isInstance(soldat, tipus)) {
                try {
                    singleton.getSessioUsuari().remove(soldat);
                    singleton.getSessioUsuari().flush();
                    Object tipusSoldat = tipus.cast(soldat);
                    Pilotada pilotada;

                    if (tipusSoldat instanceof Pilot) {
                        Pilot pilot = (Pilot) tipusSoldat;
                        pilotada = pilot.getPilotada();
                        if (pilotada == null) {
                            pilot.setPilotada(new Combat());
                        }
                    } else {
                        Mecanic mecanic = (Mecanic) tipusSoldat;
                        pilotada = mecanic.getPilotada();
                    }

                    List<Missio> missions = pilotada.getMissions();

                    logger.info("- S'han eliminat correctament els següents registres i els seus items associats:\n" + soldat.toString() + "\n          · Pilotada associada: " + pilotada + "\n          · Missions associades: " + missions + "\n");
                } catch (JDBCException ex) {
                    singleton.getSessioUsuari().getTransaction().rollback();
                }
            } else {
                logger.info("- No existeix cap registre amb aquest identificador -> " + i + "\n");
            }
        }
        singleton.getSessioUsuari().getTransaction().commit();
    }

    /**
     * Mètode que elimina un o més objectes de tipus Missio i els objectes que
     * té associats.
     * 
     * @param singleton
     * @param idInicial
     * @param idFinal 
     * @author Víctor García
     * @author Pablo Morante
     */
    public static void eliminarMissio(SingleSession singleton, int idInicial, int idFinal) {
        singleton.getSessioUsuari().beginTransaction();
        for (int i = idInicial; i <= idFinal; i++) {
            Missio missio = singleton.getSessioUsuari().get(Missio.class, i);
            if (missio != null) {
                try {
                    singleton.getSessioUsuari().remove(missio);
                    singleton.getSessioUsuari().flush();
                    List<Aeronau> aeronaus = missio.getAeronaus();

                    logger.info("S'han eliminat correctament els següents registres i els seus items associats:\n" + missio.toString() + "\n");
                    for (Aeronau item : aeronaus) {
                        List<Missio> missions = item.getMissions();
                        missions.remove(missio);
                        if (item instanceof Pilotada) {
                            Pilotada pilotada = (Pilotada) item;
                            Pilot pilot = pilotada.getPilotAeronau();
                            List<Mecanic> mecanics = pilotada.getMecanics();
                            logger.info("          · Aeronau pilotada associada: " + item + "\n          · Missions: " + missions + "\n          · Pilot: " + pilot + "\n          · Mecànics: " + mecanics + "\n");
                        } else {
                            logger.info("          · Aeronau autònoma associada: " + item + "\n          · Missions: " + missions + "\n");
                        }
                    }
                } catch (JDBCException ex) {
                    singleton.getSessioUsuari().getTransaction().rollback();
                }
            } else {
                logger.info("No existeix cap registre amb aquest identificador -> " + i);
            }
        }
        singleton.getSessioUsuari().getTransaction().commit();
    }

}
