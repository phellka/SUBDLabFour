package allPaks.workLogics;

import allPaks.models.Assembling;
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
        System.out.println("vvedite 5 dlya filtra");
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
            case 5:
                filterRead(session);
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
        Collector collector = new Collector(name, experience, session.get(Qualification.class, qualification_id));
        session.save(collector);
    }
    private void read(Session session){
        List<Collector> collectors = session.createQuery("SELECT c from Collector c", Collector.class).getResultList();
        System.out.println(collectors);
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
        Collector collector = session.get(Collector.class, id);
        collector.setName(name);
        collector.setExperience(experience);
        collector.setQualification(session.get(Qualification.class, qualification_id));
        session.save(collector);
    }
    private void filterRead(Session session){
        System.out.println("vvedite 1 dlya filtra po name");
        System.out.println("vvedite 2 dlya filtra po experience");
        System.out.println("vvedite 3 dlya filtra po qualification_id");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        List<Collector> collectors = null;
        switch(i){
            case 1:
                System.out.println("vvedite name");
                String name = scanner.next();
                collectors = session.createQuery("SELECT c from Collector c where name = \'" + name + "\'", Collector.class).getResultList();
                break;
            case 2:
                System.out.println("vvedite experience");
                int experience = scanner.nextInt();
                collectors = session.createQuery("SELECT c from Collector c  where experience = " + experience, Collector.class).getResultList();
                break;
            case 3:
                System.out.println("vvedite qualification_id");
                int qualification_id = scanner.nextInt();
                collectors = session.createQuery("SELECT c from Collector c  where qualification_id = " + qualification_id, Collector.class).getResultList();
                break;
        }
        System.out.println(collectors);
    }
    private void delete(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.println("vvedite id");
        int id = scanner.nextInt();
        Collector collector = session.get(Collector.class, id);
        AssemblingLogic assemblingLogic = new AssemblingLogic();
        assemblingLogic.delete(session, collector);
        session.delete(collector);
    }
    public void delete(Session session, Qualification qualification){
        AssemblingLogic assemblingLogic = new AssemblingLogic();
        List<Collector> collectors = session.createQuery("SELECT c from Collector c where qualification_id = " + qualification.getId(), Collector.class).getResultList();
        for(int i = 0; i < collectors.size(); ++i){
            assemblingLogic.delete(session, collectors.get(i));
            session.delete(collectors.get(i));
        }
    }
}
