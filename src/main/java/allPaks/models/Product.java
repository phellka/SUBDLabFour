package allPaks.models;


import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "price")
    private int price;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "producttype_id")
    private ProductType productType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Product(){}
    public Product(int price, String name, ProductType productType){
        this.name = name;
        this.price = price;
        this.productType = productType;
    }
    @Override
    public String toString(){
        return String.format("product [id = %d, price = %d, name = %s, producttype_id = %d]", id, price, name, productType.getId());
    }
}
