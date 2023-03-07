/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import main.SingleSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
     * @param singleton - instància de la classe SingleSession
     * @param idInicial - identificador inicial del rang
     * @param idFinal - identificador final del rang
     * @author Víctor García
     * @author Pablo Morante
     */
    public static void llistarMecanic(SingleSession singleton, int idInicial, int idFinal) {
        for (int i = idInicial; i <= idFinal; i++) {
            Mecanic mecanic = singleton.getSessioUsuari().get(Mecanic.class, i);
            if (mecanic != null) {

                Pilotada pilotada = mecanic.getPilotada();
                List<Missio> missions = pilotada.getMissions();
                logger.info("- " + mecanic.toString() + "\n          · Pilotada: " + pilotada + "\n          · Missions: " + missions + "\n");
            } else {
                logger.info("- No existeix cap registre amb aquest identificador -> " + i + "\n");
            }
        }
    }

    /***
     * (RF18) - Aquest mètode permet llistar pilots amb un rang especificat 
     * per l'usuari.
     * 
     * @param singleton - instància de la classe SingleSession
     * @param idInicial - identificador inicial del rang
     * @param idFinal - identificador final del rang
     * @author Víctor García
     * @author Pablo Morante
     */
    public static void llistarPilot(SingleSession singleton, int idInicial, int idFinal) {
        for (int i = idInicial; i <= idFinal; i++) {
            Pilot pilot = singleton.getSessioUsuari().get(Pilot.class, i);
            if (pilot != null) {

                Pilotada pilotada = pilot.getPilotada();
                if (pilot.getPilotada() == null) {
                    pilot.setPilotada(new Combat());
                }
                List<Missio> missions = pilotada.getMissions();

                logger.info("- " + pilot.toString() + "\n          · Pilotada: " + pilotada + "\n          · Missions: " + missions + "\n");
            } else {
                logger.info("- No existeix cap registre amb aquest identificador -> " + i + "\n");
            }
        }
    }

    /***
     * (RF18) - Aquest mètode permet llistar missions amb un rang especificat 
     * per l'usuari.
     * 
     * @param singleton - instància de la classe SingleSession
     * @param idInicial - identificador inicial del rang
     * @param idFinal - identificador final del rang
     * @author Víctor García
     * @author Pablo Morante
     */
    public static void llistarMissio(SingleSession singleton, int idInicial, int idFinal) {
        for (int i = idInicial; i <= idFinal; i++) {
            Missio missio = singleton.getSessioUsuari().get(Missio.class, i);
            if (missio != null) {

                List<Aeronau> aeronaus = missio.getAeronaus();

                logger.info("- " + missio.toString() + "\n          · Aeronaus associades: " + aeronaus + "\n");
            } else {
                logger.info("- No existeix cap registre amb aquest identificador -> " + i + "\n");
            }
        }
    }

    /***
     * (RF18) - Aquest mètode permet llistar drons amb un rang especificat 
     * per l'usuari.
     * 
     * @param singleton - instància de la classe SingleSession
     * @param idInicial - identificador inicial del rang
     * @param idFinal - identificador final del rang
     * @author Víctor García
     * @author Pablo Morante
     */
    public static void llistarDron(SingleSession singleton, int idInicial, int idFinal) {
        for (int i = idInicial; i <= idFinal; i++) {
            Dron dron = singleton.getSessioUsuari().get(Dron.class, i);
            if (dron != null) {

                List<Missio> missions = dron.getMissions();

                logger.info("- " + dron.toString() + "\n          · Missions: " + missions + "\n");
            } else {
                logger.info("- No existeix cap registre amb aquest identificador -> " + i + "\n");
            }
        }
    }

    /***
     * (RF18) - Aquest mètode permet llistar combats amb un rang especificat 
     * per l'usuari.
     * 
     * @param singleton - instància de la classe SingleSession
     * @param idInicial - identificador inicial del rang
     * @param idFinal - identificador final del rang
     * @author Víctor García
     * @author Pablo Morante
     */
    public static void llistarCombat(SingleSession singleton, int idInicial, int idFinal) {
        for (int i = idInicial; i <= idFinal; i++) {
            Combat combat = singleton.getSessioUsuari().get(Combat.class, i);
            if (combat != null) {

                List<Mecanic> mecanics = combat.getMecanics();
                List<Missio> missions = combat.getMissions();
                Pilot pilot = combat.getPilotAeronau();

                logger.info("- " + combat.toString() + "\n          · Pilot: " + pilot + "\n          · Mecànics: " + mecanics + "\n          · Missions: " + missions + "\n");
            } else {
                logger.info("- No existeix cap registre amb aquest identificador -> " + i + "\n");
            }
        }
    }

    /***
     * (RF18) - Aquest mètode permet llistar transports amb un rang especificat 
     * per l'usuari.
     * 
     * @param singleton - instància de la classe SingleSession
     * @param idInicial - identificador inicial del rang
     * @param idFinal - identificador final del rang
     * @author Víctor García
     * @author Pablo Morante
     */
    public static void llistarTransport(SingleSession singleton, int idInicial, int idFinal) {
        for (int i = idInicial; i <= idFinal; i++) {
            Transport transport = singleton.getSessioUsuari().get(Transport.class, i);
            if (transport != null) {

                Pilot pilot = transport.getPilotAeronau();

                List<Missio> missions = transport.getMissions();
                List<Mecanic> mecanics = transport.getMecanics();

                logger.info("- " + transport.toString() + "\n          · Pilot: " + pilot + "\n          · Mecànics: " + mecanics + "\n          · Missions: " + missions + "\n");
            } else {
                logger.info("- No existeix cap registre amb aquest identificador -> " + i + "\n");
            }
        }
    }

}
