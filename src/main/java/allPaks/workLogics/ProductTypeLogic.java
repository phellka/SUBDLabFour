package allPaks.workLogics;

import allPaks.models.Product;
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
    private void filterRead(Session session){
        System.out.println("vvedite 1 dlya filtra po name");
        System.out.println("vvedite 2 dlya filtra po gostnumber");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        List<ProductType> productTypes = null;
        switch(i){
            case 1:
                System.out.println("vvedite name");
                String name = scanner.next();
                productTypes = session.createQuery("SELECT p from ProductType p where name = \'" + name + "\'", ProductType.class).getResultList();
                break;
            case 2:
                System.out.println("vvedite gostnumber");
                int gostnumber = scanner.nextInt();
                productTypes = session.createQuery("SELECT p from ProductType p where gostnumber = " + gostnumber , ProductType.class).getResultList();
                break;
        }
        System.out.println(productTypes);
    }
    private void delete(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.println("vvedite id");
        int id = scanner.nextInt();
        ProductType productType = session.get(ProductType.class, id);
        ProductLogic productLogic = new ProductLogic();
        productLogic.delete(session, productType);
        session.delete(productType);
    }
}
