package allPaks;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class MainApp {
    public static void main(String[] args){
        SessionFactory sessionFactory = new Configuration().buildSessionFactory();


        sessionFactory.close();
    }
}
