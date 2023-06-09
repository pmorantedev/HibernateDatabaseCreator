package utils;

import entitats.Aeronau;
import entitats.Combat;
import entitats.Dron;
import entitats.Mecanic;
import entitats.Missio;
import entitats.Pilot;
import entitats.Pilotada;
import entitats.Transport;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

/**
 *
 * @author pablomorante
 */
public class LlistarClasse {

    private static final Logger logger = LogManager.getLogger(LlistatMenuClasses.class);

    /***
     * (RF18) - Aquest mètode permet llistar mecànics amb un rang especificat 
     * per l'usuari.
     * 
     * @param session
     * @param idInicial - identificador inicial del rang
     * @param idFinal - identificador final del rang
     * @author Víctor García
     * @author Pablo Morante
     */
    public static void llistarMecanic(Session session, int idInicial, int idFinal) {
        for (int i = idInicial; i <= idFinal; i++) {
            Mecanic mecanic = session.get(Mecanic.class, i);
            if (mecanic != null) {

                Pilotada pilotada = mecanic.getPilotada();
                logger.info("- " + mecanic.toString() + "\n          · Pilotada: " + pilotada + "\n");
            } else {
                logger.info("- No existeix cap Mecànic amb aquest identificador -> " + i + "\n");
            }
        }
    }

    /***
     * (RF18) - Aquest mètode permet llistar pilots amb un rang especificat 
     * per l'usuari.
     * 
     * @param session
     * @param idInicial - identificador inicial del rang
     * @param idFinal - identificador final del rang
     * @author Víctor García
     * @author Pablo Morante
     */
    public static void llistarPilot(Session session, int idInicial, int idFinal) {
        for (int i = idInicial; i <= idFinal; i++) {
            Pilot pilot = session.get(Pilot.class, i);
            if (pilot != null) {

                Pilotada pilotada = pilot.getPilotada();
                if (pilot.getPilotada() == null) {
                    pilot.setPilotada(new Combat());
                }

                logger.info("- " + pilot.toString() + "\n          · Pilotada: " + pilotada + "\n");
                
            } else {
                logger.info("- No existeix cap Pilot amb aquest identificador -> " + i + "\n");
            }
        }
    }

    /***
     * (RF18) - Aquest mètode permet llistar missions amb un rang especificat 
     * per l'usuari.
     * 
     * @param session
     * @param idInicial - identificador inicial del rang
     * @param idFinal - identificador final del rang
     * @author Víctor García
     * @author Pablo Morante
     */
    public static void llistarMissio(Session session, int idInicial, int idFinal) {
        for (int i = idInicial; i <= idFinal; i++) {
            Missio missio = session.get(Missio.class, i);
            if (missio != null) {

                List<Aeronau> aeronaus = missio.getAeronaus();

                logger.info("- " + missio.toString() + "\n");
                for (Aeronau a : aeronaus) {
                    if(a != null){
                    logger.info("          · Aeronau assignada: " + a + "\n");
                    }
                }
                //logger.info("\n");
            } else {
                logger.info("- No existeix cap Missió amb aquest identificador -> " + i + "\n");
            }
        }
    }

    /***
     * (RF18) - Aquest mètode permet llistar drons amb un rang especificat 
     * per l'usuari.
     * 
     * @param session
     * @param idInicial - identificador inicial del rang
     * @param idFinal - identificador final del rang
     * @author Víctor García
     * @author Pablo Morante
     */
    public static void llistarDron(Session session, int idInicial, int idFinal) {
        for (int i = idInicial; i <= idFinal; i++) {
            Dron dron = session.get(Dron.class, i);
            if (dron != null) {

                List<Missio> missions = dron.getMissions();

                logger.info("- " + dron.toString() + "\n");
                for (Missio m : missions) {
                    logger.info("          · Assignat a la " + m + "\n");
                }
            } else {
                logger.info("- No existeix cap Dron amb aquest identificador -> " + i + "\n");
            }
        }
    }

    /***
     * (RF18) - Aquest mètode permet llistar combats amb un rang especificat 
     * per l'usuari.
     * 
     * @param session
     * @param idInicial - identificador inicial del rang
     * @param idFinal - identificador final del rang
     * @author Víctor García
     * @author Pablo Morante
     */
    public static void llistarCombat(Session session, int idInicial, int idFinal) {
        for (int i = idInicial; i <= idFinal; i++) {
            Combat combat = session.get(Combat.class, i);
            if (combat != null) {

                List<Mecanic> mecanics = combat.getMecanics();
                List<Missio> missions = combat.getMissions();
                Pilot pilot = combat.getPilotAeronau();

                logger.info("- " + combat.toString() + "\n\n          · " + pilot + "\n");
                for (Mecanic m : mecanics) {
                    logger.info("          · " + m + "\n");
                }
                for (Missio m : missions) {
                    logger.info("          · Assignada a la " + m + "\n");
                }
            } else {
                logger.info("- No existeix cap Aeronau de Combat amb aquest identificador -> " + i + "\n");
            }
        }
    }

    /***
     * (RF18) - Aquest mètode permet llistar transports amb un rang especificat 
     * per l'usuari.
     * 
     * @param session
     * @param idInicial - identificador inicial del rang
     * @param idFinal - identificador final del rang
     * @author Víctor García
     * @author Pablo Morante
     */
    public static void llistarTransport(Session session, int idInicial, int idFinal) {
        for (int i = idInicial; i <= idFinal; i++) {
            Transport transport = session.get(Transport.class, i);
            if (transport != null) {

                Pilot pilot = transport.getPilotAeronau();

                List<Missio> missions = transport.getMissions();
                List<Mecanic> mecanics = transport.getMecanics();

                logger.info("- " + transport.toString() + "\n\n          · " + pilot + "\n");
                for (Mecanic m : mecanics) {
                    logger.info("          · " + m + "\n");
                }
                for (Missio m : missions) {
                    logger.info("          · Assignada a la " + m + "\n");
                }
            } else {
                logger.info("- No existeix cap Aeronau de Transport amb aquest identificador -> " + i + "\n");
            }
        }
    }

}
