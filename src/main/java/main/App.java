package main;

import com.github.javafaker.Faker;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;

/**
 * JavaFX App
 */
public class App {

    private static SessionFactory factory;
    private static Session session;

    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {

        menu();

        try {
            factory = new Configuration().configure("hibernate/hibernate.cfg.xml").buildSessionFactory();

            session = factory.openSession();

            session.getTransaction().begin();

            Faker faker = new Faker();

//            for (int i = 0; i < 1000; i++) {
//
//                session.save(new Dron(faker.code().ean8(), faker.harryPotter().character()));
//            }
            session.getTransaction().commit();

        } catch (ConstraintViolationException ex) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }

        } catch (HibernateException ex) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }

        } finally {

            //Tanquem la sessió
            session.close();

            //Finalitzem Hibernate
            factory.close();
        }

    }

    public static void menu() {
        
        Scanner in = new Scanner(System.in);
        int opcioMenu = 0;

        do {
            logger.info("\n" + "------------------------------------------------------------------------" + "\n"
                    + "___  ___ _____  _   _  _   _ \n"
                    + "|  \\/  ||  ___|| \\ | || | | |\n"
                    + "| .  . || |__  |  \\| || | | |\n"
                    + "| |\\/| ||  __| | . ` || | | |\n"
                    + "| |  | || |___ | |\\  || |_| |\n"
                    + "\\_|  |_/\\____/ \\_| \\_/ \\___/ \n"
                    + "                             " + "\n"
                    + "1. Generar classe" + "\n"
                    + "2. Llistar classe" + "\n"
                    + "3. Eliminar classe" + "\n"
                    + "4. Sortir" + "\n\n"
                    + "Escull una de les opcions anteriors:");

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

            logger.info("------------------------------------------------------------------------" + "\n");

            switch (opcioMenu) {
                case 1:
                    menuGenerarClasse();
                    break;
                case 2:
                    menuLlistarClasse();
                    break;
                case 3:
                    menuEliminarClasse();
                    break;
                case 4:
                    logger.info("Gràcies per utilitzar el nostre programa. Fins aviat!" + "\n");
                    System.exit(0);
                default:
                    logger.info("Número introduït no vàlid!!" + "\n"
                            + "Introdueix un dels números del menú");
            }

        } while (opcioMenu != 4);
    }

    public static void menuGenerarClasse() {
        logger.info("Classe generada");
    }

    public static void menuLlistarClasse() {
        logger.info("Classe llistada");
    }

    public static void menuEliminarClasse() {
        logger.info("Classe eliminada");
    }
}
