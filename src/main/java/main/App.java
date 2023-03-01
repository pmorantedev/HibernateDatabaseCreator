package main;

import entitats.Aeronau;
import entitats.Combat;
import entitats.Dron;
import entitats.Mecanic;
import entitats.Missio;
import entitats.Pilot;
import entitats.Pilotada;
import entitats.Transport;
import java.util.List;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.JDBCException;

public class App {

    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {

        SingleSession singleton = SingleSession.getInstance();

        menu(singleton);

    }

    /***
     * Menú principal de l'aplicació on l'usuari pot escollir què vol fer o 
     * tancar-la.
     * 
     * @param singleton
     * @author Víctor García
     */
    public static void menu(SingleSession singleton) {

        Scanner in = new Scanner(System.in);
        int opcio = 0;

        do {
            logger.info("\n" + "------------------------------------------------------------------------" + "\n"
                    + " __  __ ______ _   _ _    _ \n"
                    + "|  \\/  |  ____| \\ | | |  | |\n"
                    + "| \\  / | |__  |  \\| | |  | |\n"
                    + "| |\\/| |  __| | . ` | |  | |\n"
                    + "| |  | | |____| |\\  | |__| |\n"
                    + "|_|  |_|______|_| \\_|\\____/ \n"
                    + "                             " + "\n"
                    + "1. Generar classe" + "\n"
                    + "2. Llistar classe" + "\n"
                    + "3. Eliminar classe" + "\n"
                    + "4. Sortir" + "\n\n"
                    + ">> Escull una de les opcions anteriors:");

            opcio = (utils.ValidadorOpcioMenu.validador(in));                   //Demanem a l'usuari que introdueixi quina opció vol i comprovem que l'opció introduïda sigui un número

            logger.info("------------------------------------------------------------------------" + "\n");

            switch (opcio) {
                //Obre menú per generar classes
                case 1:
                    menuGenerarClasse(in, singleton);
                    break;
                //Obre menú per llistar classes
                case 2:
                    menuLlistarClasse(in, singleton);
                    break;
                //Obre menú per eliminar classes
                case 3:
                    menuEliminarClasse(in, singleton);
                    break;
                //Tanca l'aplicació
                case 4:
                    logger.info("Gràcies per utilitzar el nostre programa. Fins aviat!" + "\n");
                    singleton.closeSessio();
                    singleton.closeFactory();
                    System.exit(0);
                //Es mostra si l'usuari introdueix un número que no és vàlid
                default:
                    logger.info("Número introduït no vàlid!!" + "\n"
                            + "Introdueix un dels números del menú");
            }

        } while (opcio != 4);
    }

    /***
     * (RF18) - L’usuari determina quina classe vol generar i la quantitat 
     * d’aquesta. Si una entitat té entitats associades, es generen de forma 
     * automàtica i en quantitats majors de zero a escollir per l’usuari. 
     * Es mostra per pantalla a l’usuari el detall dels registres afectats.

     * 
     * @param in 
     * @param singleton 
     * @author Pablo Morante
     * @author Víctor García
     * @author Izan Jiménez 
     * @author Txell Llanas
     */
    public static void menuGenerarClasse(Scanner in, SingleSession singleton) {

        int opcioMenuGenerarClasse = 0;

        do {
            logger.info("\n" + "------------------------------------------------------------------------" + "\n"
                    + "\n" + "GENERAR CLASSE" + "\n\n"
                    + "Quina classe vols generar?");
            utils.LlistatMenuClasses.retornaClasses();

            opcioMenuGenerarClasse = utils.ValidadorOpcioMenu.validador(in);    //Demanem a l'usuari que introdueixi quina opció vol i comprovem que l'opció introduïda sigui un número

            logger.info("------------------------------------------------------------------------" + "\n");

            switch (opcioMenuGenerarClasse) {
                //Clase Combat
                case 1:
                    //pruebaGenerarClase(singleton);
                    break;
                //Clase Dron
                case 2:
                    logger.info("Classe dron generada!");
                    break;
                //Clase Mecànic
                case 3:
                    logger.info("Classe mecànic generada!");
                    break;
                //Clase Missió
                case 4:
                    logger.info("Classe missió generada!");
                    break;
                //Clase Pilot
                case 5:
                    logger.info("Classe pilot generada!");
                    break;
                //Clase Transport
                case 6:
                    logger.info("Classe transport generada!");
                    break;
                //Sortir al menú principal
                case 7:
                    break;
                default:
                    logger.info("Número introduït no vàlid!!" + "\n"
                            + "Introdueix un dels números del menú");
            }

        } while (opcioMenuGenerarClasse != 7);
    }

    /***
     * (RF18) - L’usuari determina quina classe vol llistar a través del menú  
     * i els identificadors inicials i finals d’aquesta. Si una entitat té 
     * entitats associades, també es mostren.
     * 
     * @param in 
     * @param singleton 
     * @author Pablo Morante
     * @author Víctor García
     */
    public static void menuLlistarClasse(Scanner in, SingleSession singleton) {
        int opcioMenuLlistarClasse = 0;

        do {
            logger.info("\n" + "------------------------------------------------------------------------" + "\n"
                    + "\nLLISTAR CLASSE\n\n"
                    + "Quina classe vols llistar?");
            utils.LlistatMenuClasses.retornaClasses();

            opcioMenuLlistarClasse = utils.ValidadorOpcioMenu.validador(in);    //Demanem a l'usuari que introdueixi quina opció vol i comprovem que l'opció introduïda sigui un número

            boolean ok = false;
            int idInicial = 0, idFinal = 0;
            if (opcioMenuLlistarClasse != 7) {
                do {
                    logger.info(">> Introdueix l'identificador inicial");
                    idInicial = utils.ValidadorOpcioMenu.validador(in);

                    logger.info(">> Introdueix l'identificador final");
                    idFinal = utils.ValidadorOpcioMenu.validador(in);

                    if (idFinal < idInicial) {
                        logger.info("L'identificador final no pot ser més petit que l'inicial!!\n");
                    } else {
                        ok = true;
                    }
                } while (ok != true);
            }

            logger.info("------------------------------------------------------------------------" + "\n");

            switch (opcioMenuLlistarClasse) {
                //Classe Combat
                case 1:
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
                    break;
                //Classe Dron    
                case 2:
                    for (int i = idInicial; i <= idFinal; i++) {
                        Dron dron = singleton.getSessioUsuari().get(Dron.class, i);
                        if (dron != null) {
                            
                            List<Missio> missions = dron.getMissions();

                            logger.info("- " + dron.toString() + "\n          · Missions: " + missions + "\n");
                        } else {
                            logger.info("- No existeix cap registre amb aquest identificador -> " + i + "\n");
                        }
                    }
                    break;
                //Classe Mecànic
                case 3:
                    for (int i = idInicial; i <= idFinal; i++) {
                        Mecanic mecanic = singleton.getSessioUsuari().get(Mecanic.class, i);
                        if (mecanic != null) {

                            Pilotada pilotada = mecanic.getPilotada();
                            logger.info("- " + mecanic.toString() + "\n          · Pilotada: " + pilotada + "\n");
                        } else {
                            logger.info("- No existeix cap registre amb aquest identificador -> " + i + "\n");
                        }
                    }
                    break;
                //Classe Missió
                case 4:
                    for (int i = idInicial; i <= idFinal; i++) {
                        Missio missio = singleton.getSessioUsuari().get(Missio.class, i);
                        if (missio != null) {

                            List<Aeronau> aeronaus = missio.getAeronaus();

                            logger.info("- " + missio.toString() + "\n          · Aeronau pilotades associades: " + aeronaus + "\n");
                        } else {
                            logger.info("- No existeix cap registre amb aquest identificador -> " + i + "\n");
                        }
                    }
                    break;
                //Classe Pilot
                case 5:
                    for (int i = idInicial; i <= idFinal; i++) {
                        Pilot pilot = singleton.getSessioUsuari().get(Pilot.class, i);
                        if (pilot != null) {
                            
                            Pilotada pilotada = pilot.getPilotada();
                            if (pilot.getPilotada() == null){
                                pilot.setPilotada(new Combat());
                            }
                            
                            logger.info("- " + pilot.toString() + "\n          · Pilotada: " + pilotada + "\n");
                        } else {
                            logger.info("- No existeix cap registre amb aquest identificador -> " + i + "\n");
                        }
                    }
                    break;
                //Classe Transport
                case 6:
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
                    break;
                //Sortir al menú principal
                case 7:
                    break;
                default:
                    logger.info("Número introduït no vàlid!!" + "\n"
                            + "Introdueix un dels números del menú");
            }

        } while (opcioMenuLlistarClasse != 7);
    }

       /***
     * (RF18) - L’usuari determina quina classe vol eliminar a través del menú 
     * i els identificadors inicial i final. Si una instància té instàncies 
     * associades, s'eliminen. També es mostra a l’usuari per pantalla el detall 
     * dels registres afectats.
     * 
     * @param in 
     * @param singleton 
     * @author Pablo Morante
     * @author Víctor García
     */ 
    public static void menuEliminarClasse(Scanner in, SingleSession singleton) {
        int opcioMenuEliminarClasse = 0;

        do {
            logger.info("\n" + "------------------------------------------------------------------------" + "\n"
                    + "\n" + "ELIMINAR CLASSE" + "\n\n"
                    + "Quina classe vols eliminar?");
            utils.LlistatMenuClasses.retornaClasses();

            opcioMenuEliminarClasse = utils.ValidadorOpcioMenu.validador(in);   //Demanem a l'usuari que introdueixi quina opció vol i comprovem que l'opció introduïda sigui un número

            boolean ok = false;
            int idInicial = 0, idFinal = 0;
            if (opcioMenuEliminarClasse != 7) {
                do {
                    logger.info(">> Introdueix l'identificador inicial");
                    idInicial = utils.ValidadorOpcioMenu.validador(in);

                    logger.info(">> Introdueix l'identificador final");
                    idFinal = utils.ValidadorOpcioMenu.validador(in);

                    if (idFinal < idInicial) {
                        logger.info("L'identificador final no pot ser més petit que l'inicial.\n");
                    } else {
                        ok = true;
                    }
                } while (ok != true);
            }

            logger.info("------------------------------------------------------------------------" + "\n");

            switch (opcioMenuEliminarClasse) {
                //Classe Combat
                case 1:
                    utils.EliminarAeronau.eliminarAeronau(singleton, idInicial, idFinal, Combat.class);
                    break;
                //Classe Dron
                case 2:
                    utils.EliminarAeronau.eliminarAeronau(singleton, idInicial, idFinal, Dron.class);
                    break;
                //Classe Mecànic
                case 3:
                    utils.EliminarSoldat.eliminarSoldat(singleton, idInicial, idFinal);
                    break;
                //Classe Missió
                case 4:
                    singleton.getSessioUsuari().beginTransaction();
                    for (int i = idInicial; i <= idFinal; i++) {
                        Missio missio = singleton.getSessioUsuari().get(Missio.class, i);
                        if (missio != null) {
                            try {
                                singleton.getSessioUsuari().remove(missio);
                                singleton.getSessioUsuari().flush();
                                logger.info("S'han eliminat correctament els següents registres i els seus items associats:\n" + missio.toString());
                            } catch (JDBCException ex) {
                                singleton.getSessioUsuari().getTransaction().rollback();
                            }
                        } else {
                            logger.info("No existeix cap registre amb aquest identificador -> " + i);
                        }
                    }
                    singleton.getSessioUsuari().getTransaction().commit();
                    break;
                //Classe Pilot
                case 5:
                    utils.EliminarSoldat.eliminarSoldat(singleton, idInicial, idFinal);
                    break;
                //Classe Transport
                case 6:
                    utils.EliminarAeronau.eliminarAeronau(singleton, idInicial, idFinal, Transport.class);
                    break;
                //Sortir al menú principal
                case 7:
                    break;
                default:
                    logger.info("Número introduït no vàlid!!" + "\n"
                            + "Introdueix un dels números del menú");
            }

        } while (opcioMenuEliminarClasse != 7);
    }
}
