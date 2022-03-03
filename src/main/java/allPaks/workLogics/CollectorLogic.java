package allPaks.workLogics;

import allPaks.models.Collector;
import allPaks.models.Qualification;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Scanner;

public class CollectorLogic {
    public void work(SessionFactory sessionFactory){
        System.out.println("vvedite 1 dlya sozdania collector");
        System.out.println("vvedite 2 dlya chtenya collector");
        System.out.println("vvedite 3 dlya redactirovanya collector");
        System.out.println("vvedite 4 dlya udalenya collector");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        Session session = null;
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        switch(i){
            case 1:
                create(session);
                break;
            case 2:
                read(session);
                break;
            case 3:
                update(session);
                break;
            case 4:
                delete(session);
                break;
        }
        session.getTransaction().commit();
    }
    private void create(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.println("vvedite name");
        String name = scanner.next();
        System.out.println("vvedite experience");
        int experience = scanner.nextInt();
        System.out.println("vvedite qualification_id");
        int qualification_id = scanner.nextInt();
        Collector product = new Collector(name, experience, session.get(Qualification.class, qualification_id));
        session.save(product);
    }
    private void read(Session session){
        List<Collector> products = session.createQuery("SELECT  from Collector c", Collector.class).getResultList();
        System.out.println(products);
    }
    private void update(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.println("vvedite id");
        int id = scanner.nextInt();
        System.out.println("vvedite name");
        String name = scanner.next();
        System.out.println("vvedite experience");
        int experience = scanner.nextInt();
        System.out.println("vvedite qualification_id");
        int qualification_id = scanner.nextInt();
        Collector product = session.get(Collector.class, id);
        product.setName(name);
        product.setExperience(experience);
        product.setQualification(session.get(Qualification.class, qualification_id));
        session.save(product);
    }
    private void delete(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.println("vvedite id");
        int id = scanner.nextInt();
        Collector product = session.get(Collector.class, id);
        session.remove(product);
    }
}
