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
            
            factory = new Configuration().configure("hibernateConfig/hibernate.cfg.xml").buildSessionFactory();
            
            logger.trace("Iniciem sessió...");
            session = factory.openSession();
            
            logger.trace("Iniciem transaccio...");
            session.getTransaction().begin();
            
            logger.trace("Creem objectes");
            
            Stock stock1 = new Stock("4715", "STOCK1");
            Stock stock2 = new Stock("4716", "STOCK2");
            Stock stock3 = new Stock("4717", "STOCK3");

            logger.trace("Persistim l'estat dels objectes");
            session.persist(stock1);
            session.persist(stock2);
            session.persist(stock3);
            
            logger.trace("Desem a BBDD...");
            
            logger.trace("Iniciem de nou transaccio...");
            
            session.persist(new Customer("12345678B","Juan","Perez","Lopez","666778899","Carrer tal i cual","Portal A, 3er primera","Reus","Michigan","08987","Afganistan",2345,23.8f));
            session.persist(new Customer("87654321J","Pepe","Perez","Lopez","666778890","Carrer tal i cual","Portal A, 3er primera","Reus","Michigan","08987","Afganistan",2345,23.8f));
            
            logger.info("Finalitzem transacció i desem a BBDD...");
            session.getTransaction().commit();
            
            System.out.println(stock1);
        
        } catch (ConstraintViolationException ex){
             if (session.getTransaction() !=null) 
                 session.getTransaction().rollback();
             logger.error("Violació de la restricció: " + ex.getMessage());
            
        } catch (HibernateException ex) {
            if (session.getTransaction()!=null) 
                session.getTransaction().rollback();
            
            logger.error("Excepció d'hibernate: " + ex.getMessage());
        } catch (Exception ex){
               if (session.getTransaction() !=null) 
                   session.getTransaction().rollback();
               logger.error("Excepció: " + ex.getMessage());
        }
        finally {
            
         //Tanquem la sessió
         session.close();
         
         //Finalitzem Hibernate
         factory.close();
      }
    }
}
