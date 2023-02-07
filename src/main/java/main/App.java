package main;

import entitats.Customer;
import entitats.Stock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;


public class App 
{
    private static SessionFactory factory;
    private static Session session;
    private static final Logger logger = LogManager.getLogger(App.class);
    
    public static void main( String[] args )
    {
        
        try
        {
            
            factory = new Configuration().configure().buildSessionFactory();
            
            logger.trace("Iniciem sessió...");
            session = factory.openSession();

            Integer employeeID = null;
            
            logger.trace("Iniciem transaccio...");
            session.getTransaction().begin();
            
            logger.trace("Creem objectes");
            Stock stock1 = new Stock("4715", "STOCK1");
            Stock stock2 = new Stock("4716", "STOCK2");
            Stock stock3 = new Stock("4717", "STOCK3");

            logger.trace("Persistim l'estat dels objectes");
            session.save(stock1);
            session.save(stock2);
            session.save(stock3);
            
            logger.trace("Desem a BBDD...");
            session.getTransaction().commit();
            
            logger.trace("Iniciem de nou transaccio...");
            session.beginTransaction();
            
            session.save(new Customer("12345678B","Juan","Perez","Lopez","666778899","Carrer tal i cual","Portal A, 3er primera","Reus","Michigan","08987","Afganistan",2345,23.8f));
            session.save(new Customer("12345679B","Pepe","Perez","Lopez","666778890","Carrer tal i cual","Portal A, 3er primera","Reus","Michigan","08987","Afganistan",2345,23.8f));
            
            logger.info("Finalitzem transacció i desem a BBDD...");
            session.getTransaction().commit();
        
        } catch (ConstraintViolationException ex){
             if (session.getTransaction() !=null) 
                 session.getTransaction().rollback();
             
             logger.error("Violació de la restricció: " + ex.getMessage());
            
        } catch (HibernateException ex) {
            if (session.getTransaction()!=null) 
                session.getTransaction().rollback();
            
            logger.error("Excepció d'hibernate: " + ex.getMessage());
      } finally {
            
         //Tanquem la sessió
         session.close();
         
         //Finalitzem Hibernate
         factory.close();
      }
    }
}
