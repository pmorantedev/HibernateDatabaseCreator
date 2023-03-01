/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import static com.mysql.cj.conf.PropertyKey.logger;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author victor
 */
public class ValidadorOpcioMenu {

    private static final Logger logger = LogManager.getLogger(ValidadorOpcioMenu.class);

    /***
     * Mètode que permet a l'usuari escollir una de les opcions del menú i 
     * valida que l'opció introduïda sigui un número.
     * 
     * @param in
     * @return opcioMenu - Retorna l'opció escollida per l'usuari
     * @author Víctor García
     */
    public static Integer validador(Scanner in) {

        int opcioMenu = 0;

        boolean error = false;
        boolean showError = false;
        do {
            if (in.hasNextInt()) {
                opcioMenu = in.nextInt();
                error = false;
            } else {
                in.next();
                error = true;
                if (!showError) {
                    logger.info("\n" + "Només es poden introduïr números!!" + "\n" + "Introdueix un número:");
                    showError = true;
                }
            }
        } while (error);

        showError = false;

        return opcioMenu;
    }

}
