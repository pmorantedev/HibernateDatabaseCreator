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

        logger.info("1. Missió" + "\n"
                + "2. Aeronau de Transport" + "\n"
                + "3. Aeronau de Combat" + "\n"
                + "4. Dron" + "\n"
                + "5. Pilot" + "\n"
                + "6. Mecànic" + "\n"
                + "7. Sortir al menú principal" + "\n\n"
                + ">> Escull una de les opcions anteriors:");
    }
}
