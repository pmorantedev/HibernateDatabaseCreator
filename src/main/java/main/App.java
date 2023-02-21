package main;

import com.github.javafaker.Faker;
import com.github.javafaker.Stock;
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
    
    public static void main(String[] args) {
        
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

        } catch (ConstraintViolationException ex){
             if (session.getTransaction() !=null) 
                 session.getTransaction().rollback();
             
            
        } catch (HibernateException ex) {
            if (session.getTransaction()!=null) 
                session.getTransaction().rollback();
            
      } finally {
            
         //Tanquem la sessió
         session.close();
         
         //Finalitzem Hibernate
         factory.close();
      }
        
    }

}