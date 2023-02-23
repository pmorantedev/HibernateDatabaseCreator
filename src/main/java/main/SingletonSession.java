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
public final class SingletonSession {                                           // Fer la classe 'final' per garantir que només hi hagi una sola instància d'aquesta classe

    // Atributs sessió
    private static SingletonSession instancia;
    private SessionFactory sessionFactory;
    private Session session;
    private static final Logger logger = LogManager.getLogger(SingletonSession.class);

    private SingletonSession() {                                                // Constructor privat per evitar que es pugui instanciar des de fora
        InitSessionFactory();
    }

    /**
     * Mètode per configurar la sessió d'Hibernate: defineix la connexió i crea
     * i obre la sessió.
     *
     * @author Txell Llanas: Creació/Implementació
     */
    private void InitSessionFactory() {
        Scanner in = new Scanner(System.in);
        Boolean error = false;
        String username, password, BDname;

        // Configurar la sessió d'Hibernate
        Configuration configuration = new Configuration().configure("hibernate/hibernate.cfg.xml");

        do {
            logger.info("Introdueix el nom d'usuari: ");
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
            logger.info("Introdueix la constrasenya: ");
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
            logger.info("Introdueix el nom de la BD: ");
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
    public static SingletonSession getInstance() {
        if (instancia == null) {
            instancia = new SingletonSession();
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

        return session;
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
