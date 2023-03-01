/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author victor
 */
public class LlistatMenuClasses {

    private static final Logger logger = LogManager.getLogger(LlistatMenuClasses.class);

    /***
     * Mètode que retorna un llistat de classes per als menús.
     * 
     * @author Víctor García
     */
    public static void retornaClasses() {

        logger.info("1. Combat" + "\n"
                + "2. Dron" + "\n"
                + "3. Mecànic" + "\n"
                + "4. Missió" + "\n"
                + "5. Pilot" + "\n"
                + "6. Transport" + "\n"
                + "7. Sortir al menú principal" + "\n\n"
                + ">> Escull una de les opcions anteriors:");
    }
}
