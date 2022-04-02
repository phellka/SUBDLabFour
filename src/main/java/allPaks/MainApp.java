package allPaks;

import allPaks.models.*;
import allPaks.workLogics.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.*;

import com.mongodb.*;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;


public class MainApp {
    public static void main(String[] args){/*

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
                case 3:
                    CollectorLogic collectorLogic = new CollectorLogic();
                    collectorLogic.work(sessionFactory);
                    break;
                case 4:
                    ProductLogic productLogic = new ProductLogic();
                    productLogic.work(sessionFactory);
                    break;
                case 5:
                    AssemblingLogic assemblingLogic = new AssemblingLogic();
                    assemblingLogic.work(sessionFactory);
                    break;
                case 6:
                    isWork = false;
                    break;
            }
        }*/
        SessionFactory sessionFactory = new Configuration()
                .addAnnotatedClass(Qualification.class)
                .addAnnotatedClass(ProductType.class)
                .addAnnotatedClass(Collector.class)
                .addAnnotatedClass(Product.class)
                .addAnnotatedClass(Assembling.class).buildSessionFactory();
        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = client.getDatabase("labSix");

//перенос типов продуктов
        {
            MongoCollection<Document> producttypesM = database.getCollection("producttypes");
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            List<ProductType> productTypes = session.createQuery("SELECT p from ProductType p", ProductType.class).getResultList();
            for (ProductType prd : productTypes) {
                Document newDoc = new Document("name", prd.getName()).append("gostnumber", prd.getGostNumber());
                producttypesM.insertOne(newDoc);
            }
            session.getTransaction().commit();
        }
//перенос квалификаций
        {
            MongoCollection<Document> qualificationsM = database.getCollection("qualifications");
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            List<Qualification> qualifications = session.createQuery("SELECT q from Qualification q", Qualification.class).getResultList();
            for (Qualification qual : qualifications) {
                Document newDoc = new Document("category", qual.getCategory()).append("name", qual.getName()).append("salary", qual.getSalary());
                qualificationsM.insertOne(newDoc);
            }
            session.getTransaction().commit();
        }
//перенос сборщиков
        {
            MongoCollection<Document> qualificationsM = database.getCollection("qualifications");
            MongoCollection<Document> collectorsM = database.getCollection("collectors");
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            List<Collector> collectors = session.createQuery("SELECT c from Collector c", Collector.class).getResultList();
            for (Collector col : collectors) {
                var qualId = qualificationsM.find(new Document("category", col.getQualification().getCategory())
                                .append("name", col.getQualification().getName()).append("salary", col.getQualification().getSalary()))
                                .first().get("_id");
                Document newDoc = new Document("name", col.getName()).append("experience", col.getExperience()).append("qualification_id", new ObjectId(qualId.toString()));
                collectorsM.insertOne(newDoc);
            }
            session.getTransaction().commit();
        }
//перенос продуктов
        {
            MongoCollection<Document> producttypesM = database.getCollection("producttypes");
            MongoCollection<Document> productsM = database.getCollection("products");
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            List<Product> products = session.createQuery("SELECT p from Product p", Product.class).getResultList();
            for (Product pr : products) {
                var prdId = producttypesM.find(new Document("name", pr.getProductType().getName())
                                .append("gostnumber", pr.getProductType().getGostNumber()))
                        .first().get("_id");
                Document newDoc = new Document("price", pr.getPrice()).append("name", pr.getName()).append("producttype_id", new ObjectId(prdId.toString()));
                productsM.insertOne(newDoc);
            }
            session.getTransaction().commit();
        }
//перенос сборок
        {
            MongoCollection<Document> collectorsM = database.getCollection("collectors");
            MongoCollection<Document> productsM = database.getCollection("products");
            MongoCollection<Document> assemblingsM = database.getCollection("assemblings");
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            List<Assembling> assemblings = session.createQuery("SELECT a from Assembling a", Assembling.class).getResultList();
            for (Assembling ass : assemblings) {
                var prId = productsM.find(new Document("price", ass.getProduct().getPrice()).append("name", ass.getProduct().getName()))
                        .first().get("_id");
                var colId = collectorsM.find(new Document("name", ass.getCollector().getName()).append("experience", ass.getCollector().getExperience()))
                        .first().get("_id");
                Document newDoc = new Document("quantity", ass.getQuantity()).append("date", ass.getDate())
                        .append("product_id", new ObjectId(prId.toString()))
                        .append("collector_id", new ObjectId(colId.toString()));
                assemblingsM.insertOne(newDoc);
            }
            session.getTransaction().commit();
        }
        sessionFactory.close();
    }
}
//doc.get("_id") 6221ec0b761f7c808eabac6f
//var zz = producttypes.find(new Document("name", "Наушники")).first().get("_id");
//        System.out.println(producttypes.find(new Document("_id", zz)).first());