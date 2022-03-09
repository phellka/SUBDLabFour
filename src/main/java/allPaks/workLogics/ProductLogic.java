package allPaks.workLogics;

import allPaks.models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Scanner;

public class ProductLogic {
    public void work(SessionFactory sessionFactory){
        System.out.println("vvedite 1 dlya sozdania product");
        System.out.println("vvedite 2 dlya chtenya product");
        System.out.println("vvedite 3 dlya redactirovanya product");
        System.out.println("vvedite 4 dlya udalenya product");
        System.out.println("vvedite 4 dlya filtra");
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
        System.out.println("vvedite price");
        int price = scanner.nextInt();
        System.out.println("vvedite name");
        String name = scanner.next();
        System.out.println("vvedite producttype_id");
        int producttype_id = scanner.nextInt();
        Product product = new Product(price, name, session.get(ProductType.class, producttype_id));
        session.save(product);
    }
    private void read(Session session){
        List<Product> products = session.createQuery("SELECT p from Product p", Product.class).getResultList();
        System.out.println(products);
    }
    private void update(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.println("vvedite id");
        int id = scanner.nextInt();
        System.out.println("vvedite price");
        int price = scanner.nextInt();
        System.out.println("vvedite name");
        String name = scanner.next();
        System.out.println("vvedite producttype_id");
        int producttype_id = scanner.nextInt();
        Product product = session.get(Product.class, id);
        product.setName(name);
        product.setPrice(price);
        product.setProductType(session.get(ProductType.class, producttype_id));
        session.save(product);
    }
    private void filterRead(Session session){
        System.out.println("vvedite 1 dlya filtra po name");
        System.out.println("vvedite 2 dlya filtra po price");
        System.out.println("vvedite 3 dlya filtra po producttype_id");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        List<Product> products = null;
        switch(i){
            case 1:
                System.out.println("vvedite name");
                String name = scanner.next();
                products = session.createQuery("SELECT p from Product p where name = \'" + name + "\'", Product.class).getResultList();
                break;
            case 2:
                System.out.println("vvedite experience");
                int price = scanner.nextInt();
                products = session.createQuery("SELECT p from Product p where price = " + price , Product.class).getResultList();
                break;
            case 3:
                System.out.println("vvedite qualification_id");
                int producttype_id = scanner.nextInt();
                products = session.createQuery("SELECT p from Product p where producttype_id = " + producttype_id, Product.class).getResultList();
                break;
        }
        System.out.println(products);
    }
    private void delete(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.println("vvedite id");
        int id = scanner.nextInt();
        Product product = session.get(Product.class, id);
        AssemblingLogic assemblingLogic = new AssemblingLogic();
        assemblingLogic.delete(session, product);
        session.delete(product);
    }
    public void delete(Session session, ProductType productType){
        AssemblingLogic assemblingLogic = new AssemblingLogic();
        List<Product> products = session.createQuery("SELECT p from Product p where producttype_id = " + productType.getId(), Product.class).getResultList();
        for (int i = 0; i < products.size(); ++i){
            assemblingLogic.delete(session, products.get(i));
            session.delete(products.get(i));
        }
    }
}
