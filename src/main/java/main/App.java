package main;

import com.github.javafaker.Faker;
import entitats.Aeronau;
import entitats.Combat;
import entitats.Dron;
import entitats.Mecanic;
import entitats.Missio;
import entitats.Pilot;
import entitats.Pilotada;
import entitats.Transport;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import main.SingleSession;
import org.hibernate.JDBCException;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;

/**
 * JavaFX App
 */
public class App {

    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {

        SingleSession singleton = SingleSession.getInstance();

//        Faker faker = new Faker();
//        for (int i = 0; i < 100; i++) {
//            //System.out.println(faker.aviation().aircraft());
//            System.out.println(faker.esports().game());
//        }
        menu(singleton);

    }

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

            opcio = (utils.ValidadorOpcioMenu.validador(in));

            logger.info("------------------------------------------------------------------------" + "\n");

            switch (opcio) {
                case 1:
                    menuGenerarClasse(in, singleton);
                    break;
                case 2:
                    menuLlistarClasse(in, singleton);
                    break;
                case 3:
                    menuEliminarClasse(in, singleton);
                    break;
                case 4:
                    logger.info("Gràcies per utilitzar el nostre programa. Fins aviat!" + "\n");
                    singleton.closeSessio();
                    singleton.closeFactory();
                    System.exit(0);
                default:
                    logger.info("Número introduït no vàlid!!" + "\n"
                            + "Introdueix un dels números del menú");
            }

        } while (opcio != 4);
    }

    public static void menuGenerarClasse(Scanner in, SingleSession singleton) {

        int opcioMenuGenerarClasse = 0;

        do {
            logger.info("\n" + "------------------------------------------------------------------------" + "\n"
                    + "\n" + "GENERAR CLASSE" + "\n\n"
                    + "Quina classe vols generar?");
            utils.LlistatMenuClasses.retornaClasses();

            opcioMenuGenerarClasse = utils.ValidadorOpcioMenu.validador(in);

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

    public static void menuLlistarClasse(Scanner in, SingleSession singleton) {
        int opcioMenuLlistarClasse = 0;

        do {
            logger.info("\n" + "------------------------------------------------------------------------" + "\n"
                    + "\nLLISTAR CLASSE\n\n"
                    + "Quina classe vols llistar?");
            utils.LlistatMenuClasses.retornaClasses();

            opcioMenuLlistarClasse = utils.ValidadorOpcioMenu.validador(in);

            boolean ok = false;
            int idInicial = 0, idFinal = 0;
            if (opcioMenuLlistarClasse != 7) {
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

            switch (opcioMenuLlistarClasse) {
                //Classe Combat
                case 1:
                    for (int i = idInicial; i <= idFinal; i++) {
                        Combat combat = singleton.getSessioUsuari().get(Combat.class, i);
                        if (combat != null) {

                            CriteriaBuilder cb = singleton.getSessioUsuari().getCriteriaBuilder();
                            CriteriaQuery<Mecanic> cqm = cb.createQuery(Mecanic.class);
                            Root<Mecanic> root = cqm.from(Mecanic.class);
                            cqm.where(cb.equal(root.get("pilotada"), combat.getFabricationNumber()));
                            List<Mecanic> list = singleton.getSessioUsuari().createQuery(cqm).getResultList();

                            CriteriaQuery<Missio> cqmissio = cb.createQuery(Missio.class);
                            Root<Missio> rootmissio = cqmissio.from(Missio.class);
                            Predicate predicate = rootmissio.get("aeronaus").in(combat.getMissions());
                            cqmissio.where(predicate);
                            List<Missio> listmissions = singleton.getSessioUsuari().createQuery(cqmissio).getResultList();

                            logger.info(combat.toString() + "\n          Mecànics: " + list + "\n          Missions: " + listmissions);
                        } else {
                            logger.info("No existeix cap registre amb aquest identificador -> " + i);
                        }
                    }
                    break;
                //Classe Dron    
                case 2:
                    for (int i = idInicial; i <= idFinal; i++) {
                        Dron dron = singleton.getSessioUsuari().get(Dron.class, i);
                        if (dron != null) {

                            CriteriaBuilder cb = singleton.getSessioUsuari().getCriteriaBuilder();
                            CriteriaQuery<Missio> cqmissio = cb.createQuery(Missio.class);
                            Root<Missio> rootmissio = cqmissio.from(Missio.class);
                            Predicate predicate = rootmissio.get("aeronaus").in(dron.getMissions());
                            cqmissio.where(predicate);
                            List<Missio> listmissions = new ArrayList<>(singleton.getSessio().createQuery(cqmissio).getResultList());

                            logger.info(dron.toString() + "\n          Missions: " + listmissions);
                        } else {
                            logger.info("No existeix cap registre amb aquest identificador -> " + i);
                        }
                    }
                    break;
                //Classe Mecànic
                case 3:
                    for (int i = idInicial; i <= idFinal; i++) {
                        Mecanic mecanic = singleton.getSessioUsuari().get(Mecanic.class, i);
                        if (mecanic != null) {

                            Pilotada pilotada = singleton.getSessioUsuari().get(Pilotada.class, mecanic.getPilotada());               
                            logger.info(mecanic.toString() + "\n          Pilotada: " + pilotada);
                        } else {
                            logger.info("No existeix cap registre amb aquest identificador -> " + i);
                        }
                    }
                    break;
                //Classe Missió
                case 4:
                    for (int i = idInicial; i <= idFinal; i++) {
                        Missio missio = singleton.getSessioUsuari().get(Missio.class, i);
                        if (missio != null) {

                            CriteriaBuilder cb = singleton.getSessioUsuari().getCriteriaBuilder();

                            CriteriaQuery<Missio> cqm = cb.createQuery(Missio.class);
                            Root<Missio> root = cqm.from(Missio.class);
                            Predicate predicate = cb.isNotNull(root.get("aeronaus"));
                            cqm.where(predicate);
                            Query<Missio> query = singleton.getSessioUsuari().createQuery(cqm);
                            ScrollableResults sr = query.scroll(ScrollMode.FORWARD_ONLY);
                            List<Aeronau> listaeronaus = new ArrayList<>();
                            while (sr.next()){
                                Aeronau aeronau = (Aeronau) sr.get();
                                listaeronaus.add(aeronau);
                            }
                            sr.close();

//                            CriteriaQuery<Dron> cqdron = cb.createQuery(Dron.class);
//                            Root<Dron> rootdron = cqdron.from(Dron.class);
//                            Predicate predicatedron = rootdron.get("aeronaus").in(missio.getAeronaus());
//                            cqdron.where(predicatedron);
//                            List<Dron> listdrons = singleton.getSessioUsuari().createQuery(cqdron).getResultList();
//
//                            CriteriaQuery<Transport> cqtransport = cb.createQuery(Transport.class);
//                            Root<Transport> roottransport = cqtransport.from(Transport.class);
//                            Predicate predicatetransport = roottransport.get("aeronaus").in(missio.getAeronaus());
//                            cqtransport.where(predicatetransport);
//                            List<Transport> listransport = singleton.getSessioUsuari().createQuery(cqtransport).getResultList();

                            logger.info(missio.toString() + "\n          Aeronau Pilotada de Combat: " + listaeronaus);
                        } else {
                            logger.info("No existeix cap registre amb aquest identificador -> " + i);
                        }
                    }
                    break;
                //Classe Pilot
                case 5:
                    for (int i = idInicial; i <= idFinal; i++) {
                        Pilot pilot = singleton.getSessioUsuari().get(Pilot.class, i);
                        if (pilot != null) {

                            Pilotada pilotada = singleton.getSessioUsuari().get(Pilotada.class, pilot.getPilotada());

                            logger.info(pilot.toString() + "\n          Pilotada: " + pilotada);
                        } else {
                            logger.info("No existeix cap registre amb aquest identificador -> " + i);
                        }
                    }
                    break;
                //Classe Transport
                case 6:
                    for (int i = idInicial; i <= idFinal; i++) {
                        Transport transport = singleton.getSessioUsuari().get(Transport.class, i);
                        if (transport != null) {

                            Pilot pilot = singleton.getSessioUsuari().byNaturalId(Pilot.class).using("nauPilotada_id", transport.getFabricationNumber()).load();

                            CriteriaBuilder cb = singleton.getSessioUsuari().getCriteriaBuilder();

                            CriteriaQuery<Mecanic> cqm = cb.createQuery(Mecanic.class);
                            Root<Mecanic> root = cqm.from(Mecanic.class);
                            cqm.where(cb.equal(root.get("pilotada"), transport.getFabricationNumber()));
                            List<Mecanic> list = singleton.getSessioUsuari().createQuery(cqm).getResultList();

                            CriteriaQuery<Missio> cqmissio = cb.createQuery(Missio.class);
                            Root<Missio> rootmissio = cqmissio.from(Missio.class);
                            Predicate predicate = rootmissio.get("aeronaus").in(transport.getMissions());
                            cqmissio.where(predicate);
                            List<Missio> listmissions = singleton.getSessioUsuari().createQuery(cqmissio).getResultList();

                            logger.info(transport.toString() + "\n          Pilot: " + pilot + "\n          Mecànics: " + list + "\n          Missions: " + listmissions);
                        } else {
                            logger.info("No existeix cap registre amb aquest identificador -> " + i);
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

    public static void menuEliminarClasse(Scanner in, SingleSession singleton) {
        int opcioMenuEliminarClasse = 0;

        do {
            logger.info("\n" + "------------------------------------------------------------------------" + "\n"
                    + "\n" + "ELIMINAR CLASSE" + "\n\n"
                    + "Quina classe vols eliminar?");
            utils.LlistatMenuClasses.retornaClasses();

            opcioMenuEliminarClasse = utils.ValidadorOpcioMenu.validador(in);

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
