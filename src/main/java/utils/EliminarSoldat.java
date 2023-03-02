/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import entitats.Combat;
import entitats.Mecanic;
import entitats.Pilot;
import entitats.Pilotada;
import entitats.Soldat;
import main.SingleSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.hibernate.Hibernate.isInstance;
import org.hibernate.JDBCException;

/**
 *
 * @author victor
 */
public class EliminarSoldat {

    private static final Logger logger = LogManager.getLogger(LlistatMenuClasses.class);

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

                    logger.info("- S'han eliminat correctament els següents registres i els seus items associats:\n" + soldat.toString() + "\n          · Pilotada: " + pilotada + "\n");
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
