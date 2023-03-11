package main;

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
    private String username = null;
    private String password = null;
    private String BDname = null;

    private SingleSession() {                                                // Constructor privat per evitar que es pugui instanciar des de fora
        InitSessionFactory();
    }

    private SingleSession(String username, String password, String BDname) throws org.hibernate.service.spi.ServiceException {
        this.username = username;
        this.password = password;
        this.BDname = BDname;
        InitSessionFactory();
    }

    /**
     * Mètode per configurar la sessió d'Hibernate: defineix la connexió i crea
     * i obre la sessió.
     *
     * @author Txell Llanas: Creació/Implementació
     * @author Pablo Morante: Implementació
     */
    private void InitSessionFactory() throws org.hibernate.service.spi.ServiceException {
        Configuration configuration = new Configuration().configure("hibernate/hibernate.cfg.xml");

        if (username != null && password != null && BDname != null) {
            configuration.setProperty("hibernate.connection.username", username);
            configuration.setProperty("hibernate.connection.password", password);
            configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/" + BDname + "?createDatabaseIfNotExist=true");
        }

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

    /**
     * Mètode per retornar una nova instància de 'SingleSession' amb paràmetres. Si la
     * instància encara no existeix, la crea.
     *
     * @return instancia de tipus 'SingletonSession' que obre una sessió de
     * connexió a una BD
     * @author Txell Llanas: Creació/Implementació
     * @author Pablo Morante: Implementació
     */
    public static SingleSession getInstance(String username, String password, String BDname) throws org.hibernate.service.spi.ServiceException {
        if (instancia == null) {
            instancia = new SingleSession(username, password, BDname);
        }

        return instancia;
    }

    /**
     * Mètode que retorna la mateixa sessió creada a 'InitSessionFactory'.
     *
     * @return session Instància de la mateixa sessió d'Hibernate
     * @author Txell Llanas: Creació/Implementació
     * @author Pablo Morante: Implementació
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
     * Mètode que retorna la mateixa sessió creada a 'InitSessionFactory'.
     *
     * @return session Instància de la mateixa sessió d'Hibernate
     * @author Víctor García: Creació/Implementació
     */
    public Session getSessioUsuari() {
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
