package allPaks.workLogics;

import allPaks.models.ProductType;
import allPaks.models.Qualification;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Scanner;

public class QualificationLogic {
    public void work(SessionFactory sessionFactory){
        System.out.println("vvedite 1 dlya sozdania qulification");
        System.out.println("vvedite 2 dlya chtenya qulification");
        System.out.println("vvedite 3 dlya redactirovanya qulification");
        System.out.println("vvedite 4 dlya udalenya qulification");
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
        System.out.println("vvedite category");
        int category = scanner.nextInt();
        System.out.println("vvedite name");
        String name = scanner.next();
        System.out.println("vvedite salary");
        int salary = scanner.nextInt();
        Qualification qualification = new Qualification(category, name, salary);
        session.save(qualification);
    }
    private void read(Session session){
        List<Qualification> qualifications = session.createQuery("SELECT q from Qualification q", Qualification.class).getResultList();
        System.out.println(qualifications);
    }
    private void filterRead(Session session){
        System.out.println("vvedite 1 dlya filtra po category");
        System.out.println("vvedite 2 dlya filtra po name");
        System.out.println("vvedite 3 dlya filtra po salary");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        List<Qualification> qualifications = null;
        switch(i){
            case 1:
                System.out.println("vvedite category");
                String category = scanner.next();
                qualifications = session.createQuery("SELECT q from Qualification q where category = \'" + category + "\'", Qualification.class).getResultList();
                break;
            case 2:
                System.out.println("vvedite name");
                String name = scanner.next();
                qualifications = session.createQuery("SELECT q from Qualification q where name = \'" + name + "\'", Qualification.class).getResultList();
                break;
            case 3:
                System.out.println("vvedite salary");
                int salary = scanner.nextInt();
                qualifications = session.createQuery("SELECT q from Qualification q where salary = " + salary , Qualification.class).getResultList();
                break;
        }
        System.out.println(qualifications);
    }
    private void update(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.println("vvedite id");
        int id = scanner.nextInt();
        System.out.println("vvedite category");
        int category = scanner.nextInt();
        System.out.println("vvedite name");
        String name = scanner.next();
        System.out.println("vvedite salary");
        int salary = scanner.nextInt();
        Qualification qualification = session.get(Qualification.class, id);
        qualification.setCategory(category);
        qualification.setName(name);
        qualification.setSalary(salary);
        session.save(qualification);
    }
    private void delete(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.println("vvedite id");
        int id = scanner.nextInt();
        Qualification qualification = session.get(Qualification.class, id);
        CollectorLogic collectorLogic = new CollectorLogic();
        collectorLogic.delete(session, qualification);
        session.delete(qualification);
    }
}
