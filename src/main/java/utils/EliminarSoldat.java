/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import entitats.Soldat;
import main.SingleSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.JDBCException;

/**
 *
 * @author victor
 */
public class EliminarSoldat {

    private static final Logger logger = LogManager.getLogger(LlistatMenuClasses.class);

    public static void eliminarSoldat(SingleSession singleton, int idInicial, int idFinal) {
        singleton.getSessioUsuari().beginTransaction();
        for (int i = idInicial; i <= idFinal; i++) {
            Soldat soldat = singleton.getSessioUsuari().get(Soldat.class, i);
            if (soldat != null) {
                try {
                    singleton.getSessioUsuari().remove(soldat);
                    singleton.getSessioUsuari().flush();
                    logger.info("- S'han eliminat correctament els següents registres i els seus items associats:\n" + soldat.toString());
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
