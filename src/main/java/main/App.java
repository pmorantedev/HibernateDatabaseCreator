package main;

import com.github.javafaker.Faker;
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
import java.util.List;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import main.SingleSession;

/**
 * JavaFX App
 */
public class App {

    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {

        SingleSession singleton = SingleSession.getInstance();

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
                    menuEliminarClasse(in);
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
                case 1:
                    pruebaGenerarClase(singleton);
                    break;
                case 2:
                    logger.info("Classe dron generada!");
                    break;
                case 3:
                    logger.info("Classe mecànic generada!");
                    break;
                case 4:
                    logger.info("Classe missió generada!");
                    break;
                case 5:
                    logger.info("Classe pilot generada!");
                    break;
                case 6:
                    logger.info("Classe transport generada!");
                    break;
                case 7:
                    break;
                default:
                    logger.info("Número introduït no vàlid!!" + "\n"
                            + "Introdueix un dels números del menú");
            }

        } while (opcioMenuGenerarClasse != 7);
    }

    public static void pruebaGenerarClase(SingleSession singleton) {
        try {

            singleton.getInstance().getSessio().getTransaction().begin();
            Faker faker = new Faker();

            // Test relacions Pilot-Pilotada (nau de combat)
            logger.trace("Creem objectes");
            Combat nauCombat1 = new Combat();
            nauCombat1.setNuclearPower(true);
            nauCombat1.setTotalAmmunition(12000);
            nauCombat1.setHasEjectoSeat(true);
            nauCombat1.setShellCapacity(12.7f);
            //nauCombat1.setFabricationNumber(123456);
            nauCombat1.setCorporation("Funny Bunny"); 
            nauCombat1.setEngineTorque(12.67f);
//            nauCombat1.setAutodestructionDate(date);
            nauCombat1.setHasDeathLaser(Boolean.TRUE);

            Pilot pilot1 = new Pilot();
            pilot1.setNickname("chimichurri");
            pilot1.setHealingSpeed(5.0f);
            //pilot1.setLastDrugTestDate(date);
            pilot1.setIsOtaku(false);
            pilot1.setMaxGForce(7.0f);

            // FK
            pilot1.setPilotada(nauCombat1);
            nauCombat1.setPilotAeronau(pilot1);
            System.out.println("Pilot: " + pilot1.toString());

            logger.trace("Persistim l'estat dels objectes");

            logger.info("Persistint Nau de Combat...");
            singleton.getInstance().getSessio().persist(nauCombat1);

            logger.info("Persistint Pilot...");
            singleton.getInstance().getSessio().persist(pilot1);

            Combat c1 = new Combat(true, 0, true, 0, "C1", 0, new Date(2000, 2, 25), Boolean.FALSE, pilot1);
            singleton.getInstance().getSessio().persist(c1);
            Mecanic m1 = new Mecanic("avions", 10f, c1, "sonic", 0, null, true);
            Mecanic m2 = new Mecanic("alas", 10f, c1, "mario", 0, null, true);
            Mecanic m3 = new Mecanic("aceite", 10f, c1, "liugi", 0, null, true);
            Mecanic m4 = new Mecanic("aa", 10f, c1, "aa", 0, null, true);

            singleton.getInstance().getSessio().persist(m1);
            singleton.getInstance().getSessio().persist(m2);
            singleton.getInstance().getSessio().persist(m3);
            singleton.getInstance().getSessio().persist(m4);

            for (Mecanic mecanic : c1.getMecanics()) {
                System.out.println(mecanic.getPilotada());
            }

            //singleton.getInstance().getSession().find(Combat.class, c1);
            // fi test
//            for (int i = 0; i < 1000; i++) {
//
//                singleton.getInstance().getSession().save(new Dron(faker.code().ean8(), faker.harryPotter().character()));
//            }
            logger.info("Finalitzem transacció i desem a BBDD...");
            singleton.getInstance().getSessio().getTransaction().commit();

//            
        } catch (ConstraintViolationException ex) {
            if (singleton.getInstance().getSessio().getTransaction() != null) {
                singleton.getInstance().getSessio().getTransaction().rollback();
            }

        } catch (HibernateException ex) {

            logger.info("HibernateException..." + ex.getMessage());
            if (singleton.getInstance().getSessio().getTransaction() != null) {
                singleton.getInstance().getSessio().getTransaction().rollback();
            }
        }
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
                        Combat combat = singleton.getSessio().get(Combat.class, i);
                        if (combat != null) {
                            
                            CriteriaBuilder cb = singleton.getSessio().getCriteriaBuilder();
                            CriteriaQuery<Mecanic> cqm = cb.createQuery(Mecanic.class);
                            Root<Mecanic> root = cqm.from(Mecanic.class);
                            cqm.where(cb.equal(root.get("pilotada"), combat.getFabricationNumber()));
                            List<Mecanic> list = singleton.getSessio().createQuery(cqm).getResultList();
                            
                            CriteriaQuery<Missio> cqmissio = cb.createQuery(Missio.class);
                            Root<Missio> rootmissio = cqmissio.from(Missio.class);
                            Predicate predicate = rootmissio.get("aeronaus").in(combat.getMissions());
                            cqmissio.where(predicate);
                            List<Missio> listmissions = singleton.getSessio().createQuery(cqmissio).getResultList();
                            
                            logger.info(combat.toString() + "\n          Mecànics: " + list + "\n          Missions: " + listmissions);
                        } else {
                            logger.info("No existeix cap registre amb aquest identificador -> " + i);
                        }
                    }
                    break;
                //Classe Dron    
                case 2:
                    for (int i = idInicial; i <= idFinal; i++) {
                        Dron dron = singleton.getSessio().get(Dron.class, i);
                        if (dron != null) {
                            
                            CriteriaBuilder cb = singleton.getSessio().getCriteriaBuilder();
                            CriteriaQuery<Missio> cqmissio = cb.createQuery(Missio.class);
                            Root<Missio> rootmissio = cqmissio.from(Missio.class);
                            Predicate predicate = rootmissio.get("aeronaus").in(dron.getMissions());
                            cqmissio.where(predicate);
                            List<Missio> listmissions = singleton.getSessio().createQuery(cqmissio).getResultList();
                            
                            logger.info(dron.toString() + "\n          Missions: " + listmissions);
                        } else {
                            logger.info("No existeix cap registre amb aquest identificador -> " + i);
                        }
                    }
                    break;
                //Classe Mecànic
                case 3:
                    for (int i = idInicial; i <= idFinal; i++) {
                        Mecanic mecanic = singleton.getSessio().get(Mecanic.class, i);
                        if (mecanic != null) {
                            
                            Pilotada pilotada = singleton.getSessio().get(Pilotada.class, mecanic.getPilotada());
                            
//                            CriteriaBuilder cb = singleton.getSessio().getCriteriaBuilder();
//                            
//                            CriteriaQuery<Pilotada> cqpilotada = cb.createQuery(Pilotada.class);
//                            Root<Pilotada> rootpilotada = cqpilotada.from(Pilotada.class);
//                            Predicate predicatepilotada = rootpilotada.get("mecanics").in(mecanic.getPilotada());
//                            cqpilotada.where(predicatepilotada);
//                            List<Pilotada> list = singleton.getSessio().createQuery(cqpilotada).getResultList();
//                            
                            logger.info(mecanic.toString() + "\n          Pilotada: " + pilotada);
                        } else {
                            logger.info("No existeix cap registre amb aquest identificador -> " + i);
                        }
                    }
                    break;
                //Classe Missió
                case 4:
                    for (int i = idInicial; i <= idFinal; i++) {
                        Missio missio = singleton.getSessio().get(Missio.class, i);
                        if (missio != null) {
                            
                            CriteriaBuilder cb = singleton.getSessio().getCriteriaBuilder();
                            
                            CriteriaQuery<Combat> cqm = cb.createQuery(Combat.class);
                            Root<Combat> root = cqm.from(Combat.class);
                            Predicate predicate = root.get("aeronaus").in(missio.getAeronaus());
                            cqm.where(predicate);
                            List<Combat> list = singleton.getSessio().createQuery(cqm).getResultList();
                            
                            CriteriaQuery<Dron> cqdron = cb.createQuery(Dron.class);
                            Root<Dron> rootdron = cqdron.from(Dron.class);
                            Predicate predicatedron = rootdron.get("aeronaus").in(missio.getAeronaus());
                            cqdron.where(predicatedron);
                            List<Dron> listdrons = singleton.getSessio().createQuery(cqdron).getResultList();
                            
                            CriteriaQuery<Transport> cqtransport = cb.createQuery(Transport.class);
                            Root<Transport> roottransport = cqtransport.from(Transport.class);
                            Predicate predicatetransport = roottransport.get("aeronaus").in(missio.getAeronaus());
                            cqtransport.where(predicatetransport);
                            List<Transport> listransport = singleton.getSessio().createQuery(cqtransport).getResultList();
                            
                            logger.info(missio.toString() + "\n          Dron: " + listdrons + "\n          Aeronau Pilotada de Transport: " + listransport + "\n          Aeronua Pilotada de Combat: " + list);
                        } else {
                            logger.info("No existeix cap registre amb aquest identificador -> " + i);
                        }
                    }
                    break;
                //Classe Pilot
                case 5:
                    for (int i = idInicial; i <= idFinal; i++) {
                        Pilot pilot = singleton.getSessio().get(Pilot.class, i);
                        if (pilot != null) {
                            
                            Pilotada pilotada = singleton.getSessio().get(Pilotada.class, pilot.getPilotada());
//                            CriteriaBuilder cb = singleton.getSessio().getCriteriaBuilder();
//                            
//                            CriteriaQuery<Pilotada> cqp = cb.createQuery(Pilotada.class);
//                            Root<Pilotada> root = cqp.from(Pilotada.class);
//                            cqp.where(cb.equal(root.get("pilot"), pilot.getOperatingNumber()));
//                            List<Pilotada> list = singleton.getSessio().createQuery(cqp).getResultList();
                            
                            logger.info(pilot.toString() + "\n          Pilotada: " + pilotada);
                        } else {
                            logger.info("No existeix cap registre amb aquest identificador -> " + i);
                        }
                    }
                    break;
                //Classe Transport
                case 6:
                    for (int i = idInicial; i <= idFinal; i++) {
                        Transport transport = singleton.getSessio().get(Transport.class, i);
                        if (transport != null) {
                            
                            Pilot pilot = singleton.getSessio().byNaturalId(Pilot.class).using("nauPilotada_id", transport.getFabricationNumber()).load();
                            
                            CriteriaBuilder cb = singleton.getSessio().getCriteriaBuilder();
                            
                            CriteriaQuery<Mecanic> cqm = cb.createQuery(Mecanic.class);
                            Root<Mecanic> root = cqm.from(Mecanic.class);
                            cqm.where(cb.equal(root.get("pilotada"), transport.getFabricationNumber()));
                            List<Mecanic> list = singleton.getSessio().createQuery(cqm).getResultList();
                            
                            CriteriaQuery<Missio> cqmissio = cb.createQuery(Missio.class);
                            Root<Missio> rootmissio = cqmissio.from(Missio.class);
                            Predicate predicate = rootmissio.get("aeronaus").in(transport.getMissions());
                            cqmissio.where(predicate);
                            List<Missio> listmissions = singleton.getSessio().createQuery(cqmissio).getResultList();
                            
                            logger.info(transport.toString() + "\n          Pilot: " + pilot + "\n          Mecànics: " + list + "\n          Missions: " + listmissions);
                        } else {
                            logger.info("No existeix cap registre amb aquest identificador -> " + i);
                        }
                    }                    
                    break;
                case 7:
                    break;
                default:
                    logger.info("Número introduït no vàlid!!" + "\n"
                            + "Introdueix un dels números del menú");
            }

        } while (opcioMenuLlistarClasse != 7);
    }

    public static void menuEliminarClasse(Scanner in) {
        int opcioMenuEliminarClasse = 0;

        do {
            logger.info("\n" + "------------------------------------------------------------------------" + "\n"
                    + "\n" + "ELIMINAR CLASSE" + "\n\n"
                    + "Quina classe vols eliminar?");
            utils.LlistatMenuClasses.retornaClasses();

            opcioMenuEliminarClasse = utils.ValidadorOpcioMenu.validador(in);

            logger.info("------------------------------------------------------------------------" + "\n");

            switch (opcioMenuEliminarClasse) {
                case 1:
                    logger.info("Classe combat eliminada!");
                    break;
                case 2:
                    logger.info("Classe dron eliminada!");
                    break;
                case 3:
                    logger.info("Classe mecànic eliminada!");
                    break;
                case 4:
                    logger.info("Classe missió eliminada!");
                    break;
                case 5:
                    logger.info("Classe pilot eliminada!");
                    break;
                case 6:
                    logger.info("Classe transport eliminada!");
                    break;
                case 7:
                    break;
                default:
                    logger.info("Número introduït no vàlid!!" + "\n"
                            + "Introdueix un dels números del menú");
            }

        } while (opcioMenuEliminarClasse != 7);
    }
}
