package allPaks.workLogics;

import allPaks.models.ProductType;
import allPaks.models.Qualification;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Scanner;

public class ProductTypeLogic {
    public void work(SessionFactory sessionFactory){
        System.out.println("vvedite 1 dlya sozdania producttype");
        System.out.println("vvedite 2 dlya chtenya producttype");
        System.out.println("vvedite 3 dlya redactirovanya producttype");
        System.out.println("vvedite 4 dlya udalenya producttype");
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
        System.out.println("vvedite gostnumber");
        int gostNumber = scanner.nextInt();
        ProductType productType = new ProductType(name, gostNumber);
        session.save(productType);
    }
    private void read(Session session){
        List<ProductType> productTypes = session.createQuery("SELECT p from ProductType p", ProductType.class).getResultList();
        System.out.println(productTypes);
    }
    private void update(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.println("vvedite id");
        int id = scanner.nextInt();
        System.out.println("vvedite name");
        String name = scanner.next();
        System.out.println("vvedite gostnumber");
        int gostNumber = scanner.nextInt();
        ProductType productType = session.get(ProductType.class, id);
        productType.setName(name);
        productType.setGostNumber(gostNumber);
        session.save(productType);
    }
    private void delete(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.println("vvedite id");
        int id = scanner.nextInt();
        ProductType productType = session.get(ProductType.class, id);
        session.remove(productType);
    }
}
