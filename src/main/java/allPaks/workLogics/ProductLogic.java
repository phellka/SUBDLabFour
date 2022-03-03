package allPaks.workLogics;

import allPaks.models.Collector;
import allPaks.models.Product;
import allPaks.models.ProductType;
import allPaks.models.Qualification;
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
    private void delete(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.println("vvedite id");
        int id = scanner.nextInt();
        Product product = session.get(Product.class, id);
        session.remove(product);
    }
}
