package allPaks.workLogics;

import allPaks.models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class AssemblingLogic {
    public void work(SessionFactory sessionFactory){
        System.out.println("vvedite 1 dlya sozdania assemblingLogic");
        System.out.println("vvedite 2 dlya chtenya assemblingLogic");
        System.out.println("vvedite 3 dlya redactirovanya assemblingLogic");
        System.out.println("vvedite 4 dlya udalenya assemblingLogic");
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
        System.out.println("vvedite quantity");
        int quantity = scanner.nextInt();
        System.out.println("vvedite date");
        String dateS = scanner.next();
        //????
        Date date = Date.from(LocalDate.parse(dateS).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        //???
        System.out.println("vvedite collector_id");
        int collector_id = scanner.nextInt();
        System.out.println("vvedite product_id");
        int product_id = scanner.nextInt();
        Assembling assembling = new Assembling(quantity, date, session.get(Collector.class, collector_id),
                session.get(Product.class, product_id));
        session.save(assembling);
    }
    private void read(Session session){
        List<Assembling> assemblings = session.createQuery("SELECT a from Assembling a", Assembling.class).getResultList();
        System.out.println(assemblings);
    }
    private void update(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.println("vvedite id");
        int id = scanner.nextInt();
        System.out.println("vvedite quantity");
        int quantity = scanner.nextInt();
        System.out.println("vvedite date");
        String dateS = scanner.next();
        Date date = Date.from(LocalDate.parse(dateS).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        System.out.println("vvedite collector_id");
        int collector_id = scanner.nextInt();
        System.out.println("vvedite product_id");
        int product_id = scanner.nextInt();
        Assembling assembling = session.get(Assembling.class, id);
        assembling.setQuantity(quantity);
        assembling.setDate(date);
        assembling.setCollector(session.get(Collector.class, collector_id));
        assembling.setProduct(session.get(Product.class, product_id));
        session.save(assembling);
    }
    private void delete(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.println("vvedite id");
        int id = scanner.nextInt();
        Assembling assembling = session.get(Assembling.class, id);
        session.delete(assembling);
    }
    private void filterRead(Session session){
        System.out.println("vvedite 1 dlya filtra po quantity");
        System.out.println("vvedite 2 dlya filtra po date");
        System.out.println("vvedite 3 dlya filtra po collector_id");
        System.out.println("vvedite 4 dlya filtra po product_id");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        List<Assembling> assemblings = null;
        switch(i){
            case 1:
                System.out.println("vvedite quantity");
                int quantity = scanner.nextInt();
                assemblings = session.createQuery("SELECT a from Assembling a where quantity = " + quantity, Assembling.class).getResultList();
                break;
            case 2:
                System.out.println("vvedite date");
                String dateS = scanner.next();
                assemblings = session.createQuery("SELECT a from Assembling a where date = \'" + dateS + "\'", Assembling.class).getResultList();
                break;
            case 3:
                System.out.println("vvedite collector_id");
                int collector_id = scanner.nextInt();
                assemblings = session.createQuery("SELECT a from Assembling a where collector_id = " + collector_id, Assembling.class).getResultList();
                break;
            case 4:
                System.out.println("vvedite product_id");
                int product_id = scanner.nextInt();
                assemblings = session.createQuery("SELECT a from Assembling a where product_id = " + product_id, Assembling.class).getResultList();
                break;
        }
        System.out.println(assemblings);
    }
    public void delete(Session session, Collector collector){
        List<Assembling> assemblings = session.createQuery("SELECT a from Assembling a where collector_id = " + collector.getId(), Assembling.class).getResultList();
        for (int i = 0; i < assemblings.size(); ++i){
            session.delete(assemblings.get(i));
        }
    }
    public void delete(Session session, Product product){
        List<Assembling> assemblings = session.createQuery("SELECT a from Assembling a where product_id = " + product.getId(), Assembling.class).getResultList();
        for (int i = 0; i < assemblings.size(); ++i){
            session.delete(assemblings.get(i));
        }
    }
}
