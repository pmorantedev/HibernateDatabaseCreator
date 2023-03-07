package utils;

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
     * @param in - objecte Scanner per llegir la entrada de l'usuari
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
    
    /***
     * Aquest mètode demana a l'usuari que introdueixi un nombre enter vàlid
     * que representi el nombre d'aeronaus en una missió determinada.
     * També assegura que compleix la cardinalitat.
     * 
     * @param in - objecte Scanner per llegir la entrada de l'usuari
     * @return totalAeronaus - retorna el nombre d'aeronaus en la missió
     * @author Txell Llanas
     */
    public static Integer numAeronausMissio(Scanner in) {

        int totalAeronaus = 0;
        
        boolean error = false;
        boolean showError = false;
        do {
            if (!in.hasNextInt()) {
                
                in.next();
                error = true;
                if (!showError) {
                    logger.info("\n" + "Només es poden introduïr números!!" + "\n" + "Introdueix un número:");
                    showError = true;
                }
                    
            } else {
           
                int valor = in.nextInt();
                if ( valor <= 0 || valor > 8) {                                 // Quantitat incorrecta introduïda
                    logger.info("\n[AVÍS!] Cal introduir un nombre enter entre 1 i 8."
                              + "\n>> Nº d'aeronaus: ");
                    error = true;
                    
                } else {
                    totalAeronaus = valor;
                    error = false;
                }
            }
                
        } while (error);

        showError = false;

        return totalAeronaus;
    
    }

    
        /***
     * Aquest mètode demana a l'usuari que introdueixi un nombre enter vàlid
     * que representi el nombre de mecànics per aeronau.
     * També assegura que compleix la cardinalitat.
     * 
     * @param in - objecte Scanner per llegir la entrada de l'usuari
     * @return totalAeronaus - retorna el nombre d'aeronaus en la missió
     * @author Txell Llanas
     */
    public static Integer numMecanicAeronau(Scanner in) {

        int totalMecanics = 0;
        
        boolean error = false;
        boolean showError = false;
        do {
            if (!in.hasNextInt()) {
                
                in.next();
                error = true;
                if (!showError) {
                    logger.info("\n" + "Només es poden introduïr números!!" + "\n" + "Introdueix un número:");
                    showError = true;
                }
                    
            } else {
           
                int valor = in.nextInt();
                if ( valor <= 0 || valor > 2) {                                 // Quantitat incorrecta introduïda
                    logger.info("\n[AVÍS!] Cal introduir un nombre enter entre 1 i 2."
                              + "\n>> Nº de mecànics: ");
                    error = true;
                    
                } else {
                    totalMecanics = valor;
                    error = false;
                }
            }
                
        } while (error);

        showError = false;

        return totalMecanics;
    
    }
    
}
