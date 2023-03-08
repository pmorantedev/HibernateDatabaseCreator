package main;

import entitats.Combat;
import entitats.Dron;
import entitats.Mecanic;
import entitats.Pilot;
import entitats.Transport;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import utils.GenerarClasse;

public class App {

    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {

        SingleSession singleton = menuLogin();

        menu(singleton);

    }

    /**
     * *
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

    /**
     * *
     * (RF18) - L’usuari determina quina classe vol generar i la quantitat
     * d’aquesta. Si una entitat té entitats associades, es generen de forma
     * automàtica i en quantitats majors de zero a escollir per l’usuari. Es
     * mostra per pantalla a l’usuari el detall dels registres afectats.
     *
     * @param in    Valor enter introduït per l'usuari
     * @param singleton Instància de la sessió actual
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
                //Clase Missio
                case 1:
                    GenerarClasse.iniciarGeneracions(1);
                    break;
                //Clase Transport
                case 2:
                    GenerarClasse.iniciarGeneracions(2);
                    break;
                //Clase Combat
                case 3:
                    GenerarClasse.iniciarGeneracions(3);
                    break;
                //Clase Dron
                case 4:
                    GenerarClasse.iniciarGeneracions(4);
                    break;
                //Clase Pilot
                case 5:
                    GenerarClasse.iniciarGeneracions(5);
                    break;
                //Clase Mecanic
                case 6:
                    GenerarClasse.iniciarGeneracions(6);
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

    /**
     * *
     * (RF18) - L’usuari determina quina classe vol llistar a través del menú i
     * els identificadors inicials i finals d’aquesta. Si una entitat té
     * entitats associades, també es mostren.
     *
     * @param in
     * @param singleton
     * @author Pablo Morante
     * @author Víctor García
     */
    public static void menuLlistarClasse(Scanner in, SingleSession singleton) {
        Session session = SingleSession.getInstance().getSessio();
        int opcioMenuLlistarClasse = 0;

        do {
            logger.info("\n" + "------------------------------------------------------------------------" + "\n"
                    + "\nLLISTAR CLASSE\n\n"
                    + "Quina classe vols llistar?");
            utils.LlistatMenuClasses.retornaClasses();

            do {
                opcioMenuLlistarClasse = utils.ValidadorOpcioMenu.validador(in);    //Demanem a l'usuari que introdueixi quina opció vol i comprovem que l'opció introduïda sigui un número
                if (opcioMenuLlistarClasse <= 0 || opcioMenuLlistarClasse > 7) {
                    logger.info("Número introduït no vàlid!!" + "\n"
                            + "Introdueix un dels números del menú");
                }
            } while (opcioMenuLlistarClasse <= 0 || opcioMenuLlistarClasse > 7);

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
                //Classe Missió
                case 1:
                    utils.LlistarClasse.llistarMissio(session, idInicial, idFinal);
                    break;
                //Classe Transport    
                case 2:
                    utils.LlistarClasse.llistarTransport(session, idInicial, idFinal);
                    break;
                //Classe Combat
                case 3:
                    utils.LlistarClasse.llistarCombat(session, idInicial, idFinal);
                    break;
                //Classe Dron
                case 4:
                    utils.LlistarClasse.llistarDron(session, idInicial, idFinal);
                    break;
                //Classe Pilot
                case 5:
                    utils.LlistarClasse.llistarPilot(session, idInicial, idFinal);
                    break;
                //Classe Mecànic
                case 6:
                    utils.LlistarClasse.llistarMecanic(session, idInicial, idFinal);
                    break;
                //Sortir al menú principal
                case 7:
                    session.close();
                    break;
            }

        } while (opcioMenuLlistarClasse != 7);
    }

    /**
     * *
     * (RF18) - L’usuari determina quina classe vol eliminar a través del menú i
     * els identificadors inicial i final. Si una instància té instàncies
     * associades, s'eliminen. També es mostra a l’usuari per pantalla el detall
     * dels registres afectats.
     *
     * @param in
     * @param singleton
     * @author Pablo Morante
     * @author Víctor García
     */
    public static void menuEliminarClasse(Scanner in, SingleSession singleton) {
        Session session = SingleSession.getInstance().getSessio();
        int opcioMenuEliminarClasse = 0;

        do {
            logger.info("\n" + "------------------------------------------------------------------------" + "\n"
                    + "\n" + "ELIMINAR CLASSE" + "\n\n"
                    + "Quina classe vols eliminar?");
            utils.LlistatMenuClasses.retornaClasses();

            do {
                opcioMenuEliminarClasse = utils.ValidadorOpcioMenu.validador(in);   //Demanem a l'usuari que introdueixi quina opció vol i comprovem que l'opció introduïda sigui un número
                if (opcioMenuEliminarClasse <= 0 || opcioMenuEliminarClasse > 7) {
                    logger.info("Número introduït no vàlid!!" + "\n"
                            + "Introdueix un dels números del menú");
                }
            } while (opcioMenuEliminarClasse <= 0 || opcioMenuEliminarClasse > 7);

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
                //Classe Missió
                case 1:
                    utils.EliminarClasse.eliminarMissio(session, idInicial, idFinal);
                    break;
                //Classe Transport
                case 2:
                    utils.EliminarClasse.eliminarAeronau(session, idInicial, idFinal, Transport.class);
                    break;
                //Classe Combat
                case 3:
                    utils.EliminarClasse.eliminarAeronau(session, idInicial, idFinal, Combat.class);
                    break;
                //Classe Dron
                case 4:
                    utils.EliminarClasse.eliminarAeronau(session, idInicial, idFinal, Dron.class);
                    break;
                //Classe Pilot
                case 5:
                    utils.EliminarClasse.eliminarSoldat(session, idInicial, idFinal, Pilot.class);
                    break;
                //Classe Mecànic
                case 6:
                    utils.EliminarClasse.eliminarSoldat(session, idInicial, idFinal, Mecanic.class);
                    break;
                //Sortir al menú principal
                case 7:
                    session.close();
                    break;
            }

        } while (opcioMenuEliminarClasse != 7);
    }

    /**
     * Mètode per mostrar el login a l'usuari, s'accedeix a la base de dades a
     * través de les dades introduïdes per l'usuari
     *
     * @return SingleSession
     * @author Pablo Morante
     * @author Víctor García - UI
     */
    public static SingleSession menuLogin() {
        Scanner in = new Scanner(System.in);
        Boolean error = false;
        Boolean loginError = false;
        String username, password, BDname;
        SingleSession singleton = null;

        do {
            logger.info("  _      ____   _____ _____ _   _ \n"
                    + " | |    / __ \\ / ____|_   _| \\ | |\n"
                    + " | |   | |  | | |  __  | | |  \\| |\n"
                    + " | |   | |  | | | |_ | | | | . ` |\n"
                    + " | |___| |__| | |__| |_| |_| |\\  |\n"
                    + " |______\\____/ \\_____|_____|_| \\_|\n");
            do {
                logger.info("\n" + ">> Introdueix el nom d'usuari: ");
                username = in.nextLine();
                if (username == null) {
                    error = true;
                } else if (username.isEmpty()) {
                    error = true;
                } else {
                    error = false;
                }

            } while (error);

            do {
                logger.info("\n" + ">> Introdueix la constrasenya: ");
                password = in.nextLine();
                if (password == null) {
                    error = true;
                } else if (password.isEmpty()) {
                    error = true;
                } else {
                    error = false;
                }
            } while (error);

            do {
                logger.info("\n" + ">> Introdueix el nom de la BD: ");
                BDname = in.nextLine();
                if (BDname == null) {
                    error = true;
                } else if (BDname.isEmpty()) {
                    error = true;
                } else {
                    error = false;
                }
            } while (error);
            try {
                singleton = SingleSession.getInstance(username, password, BDname);
                loginError = false;
            } catch (org.hibernate.service.spi.ServiceException ex) {
                logger.info(">> Nom d'usuari o contrasenya incorrectes.");
                loginError = true;
            }

        } while (loginError);

        return singleton;
    }
}
