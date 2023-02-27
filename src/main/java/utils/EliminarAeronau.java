/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import entitats.Aeronau;
import main.SingleSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.JDBCException;

/**
 *
 * @author victor
 */
public class EliminarAeronau {
    
    private static final Logger logger = LogManager.getLogger(LlistatMenuClasses.class);
    
    public static void eliminarAeronau(SingleSession singleton, int idInicial, int idFinal){
        singleton.getSessio().beginTransaction();
        for (int i = idInicial; i <= idFinal; i++) {
            Aeronau combat = singleton.getSessio().get(Aeronau.class, i);
            if (combat != null) {
                try {
                    singleton.getSessio().remove(combat);
                    singleton.getSessio().flush();
                    logger.info("S'han eliminat correctament els següents registres i els seus items associats:\n" + combat.toString());
                } catch (JDBCException ex) {
                    singleton.getSessio().getTransaction().rollback();
                }
            } else {
                logger.info("No existeix cap registre amb aquest identificador -> " + i);
            }
        }
        singleton.getSessio().getTransaction().commit();
    }
}
