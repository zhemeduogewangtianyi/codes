package factory;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateFactory {

    private static Configuration configuration = new Configuration().configure();

    private static SessionFactory sessionFactory = configuration.buildSessionFactory();

    public static Session getSession(){
        return sessionFactory.openSession();
    }

    public static void close(Session session){
        session.close();
        sessionFactory.close();
    }

}
