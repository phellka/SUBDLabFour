package allPaks;

import allPaks.models.ProductType;
import allPaks.models.Qualification;
import allPaks.workLogics.ProductTypeLogic;
import allPaks.workLogics.QualificationLogic;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args){
        SessionFactory sessionFactory = new Configuration().addAnnotatedClass(Qualification.class)
                .addAnnotatedClass(ProductType.class).buildSessionFactory();
        boolean isWork = true;
        while(isWork){
            System.out.println("vvedite 1 dlya raboty s qualifications");
            System.out.println("vvedite 2 dlya raboty s producttypes");
            System.out.println("vvedite 3 dlya raboty s collectors");
            System.out.println("vvedite 4 dlya raboty s products");
            System.out.println("vvedite 5 dlya raboty s assemblings");
            System.out.println("vvedite 6 dlya vyxoda");
            Scanner scanner = new Scanner(System.in);
            int i = scanner.nextInt();
            switch (i){
                case 1:
                    QualificationLogic qualificationLogic = new QualificationLogic();
                    qualificationLogic.work(sessionFactory);
                    break;
                case 2:
                    ProductTypeLogic productTypeLogic = new ProductTypeLogic();
                    productTypeLogic.work(sessionFactory);
                    break;
                case 6:
                    isWork = false;
                    break;
            }
        }

        sessionFactory.close();
    }
}
