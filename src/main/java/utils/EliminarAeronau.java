/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import entitats.Aeronau;
import entitats.Mecanic;
import entitats.Missio;
import entitats.Pilot;
import entitats.Pilotada;
import main.SingleSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.hibernate.Hibernate.isInstance;
import org.hibernate.JDBCException;
import java.util.List;

/**
 *
 * @author victor
 */
public class EliminarAeronau {
    
    private static final Logger logger = LogManager.getLogger(LlistatMenuClasses.class);
    
    /***
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
    public static void eliminarAeronau(SingleSession singleton, int idInicial, int idFinal, Class<?> tipus){
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
}
