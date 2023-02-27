package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Classe 'SingletonSession' que representa una única sessió d'Hibernate.
 * Utilitza el patró Singleton per garantir que només es creï una sola instància
 * de la sessió.
 *
 * @author Txell Llanas: Creació/Implementació
 */
public final class SingleSession {                                           // Fer la classe 'final' per garantir que només hi hagi una sola instància d'aquesta classe

    // Atributs sessió
    private static SingleSession instancia;
    private SessionFactory sessionFactory;
    private Session session;
    private static final Logger logger = LogManager.getLogger(SingleSession.class);

    private SingleSession() {                                                // Constructor privat per evitar que es pugui instanciar des de fora
        InitSessionFactory();
    }

    private SingleSession(Boolean user) {
        InitSessionFactory(user);
    }

    private void InitSessionFactory(Boolean user) {
        Scanner in = new Scanner(System.in);
        Boolean error = false;
        Boolean loginError = false;
        String username, password, BDname;

        do {
            // Configurar la sessió d'Hibernate
            Configuration configuration = new Configuration().configure("hibernate/hibernate.cfg.xml");
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

            configuration.setProperty("hibernate.connection.username", username);
            configuration.setProperty("hibernate.connection.password", password);
            configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/" + BDname + "?createDatabaseIfNotExist=true");
            try {
                this.sessionFactory = configuration.buildSessionFactory();
                loginError = false;
            } catch (org.hibernate.service.spi.ServiceException ex) {
                logger.info(">> Nom d'usuari o contrasenya incorrectes.");
                loginError = true;
            }
        } while (loginError);

        this.session = sessionFactory.openSession();
    }

    /**
     * Mètode per configurar la sessió d'Hibernate: defineix la connexió i crea
     * i obre la sessió.
     *
     * @author Txell Llanas: Creació/Implementació
     */
    private void InitSessionFactory() {
        Configuration configuration = new Configuration().configure("hibernate/hibernate.cfg.xml");
        this.sessionFactory = configuration.buildSessionFactory();

        this.session = sessionFactory.openSession();

    }

    /**
     * Mètode per retornar una nova instància de 'SingleSession'. Si la
     * instància encara no existeix, la crea.
     *
     * @return instancia de tipus 'SingletonSession' que obre una sessió de
     * connexió a una BD
     * @author Txell Llanas: Creació/Implementació
     */
    public static SingleSession getInstance() {
        if (instancia == null) {
            instancia = new SingleSession();
        }

        return instancia;
    }

    public static SingleSession getInstance(Boolean user) {
        if (instancia == null) {
            instancia = new SingleSession(user);
        }

        return instancia;
    }

    /**
     * Mètode que retorna la mateixa sessió creada a 'InitSessionFactory'.
     *
     * @return session Instància de la mateixa sessió d'Hibernate
     * @author Txell Llanas: Creació/Implementació
     */
    public Session getSessio() {
        if (session.getTransaction() != null) {
            session.close();
            sessionFactory.close();
            InitSessionFactory();
        }

        return session;
    }

    /**
     * Mètode per tancar la sessió actual d'Hibernate.
     *
     * @author Víctor García: Creació/Implementació
     */
    public void closeSessio() {
        session.close();
    }

    /**
     * Mètode per tancar la sessió actual d'Hibernate.
     *
     * @author Txell Llanas: Creació/Implementació
     */
    public void closeFactory() {
        sessionFactory.close();
    }
}
