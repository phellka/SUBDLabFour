package allPaks.models;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "assemblings")
public class Assembling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "date")
    private Date date;
    @ManyToOne
    @JoinColumn(name = "collector_id")
    private Collector collector;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Collector getCollector() {
        return collector;
    }

    public void setCollector(Collector collector) {
        this.collector = collector;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Assembling(){}
    public Assembling(int quantity, Date date, Collector collector, Product product){
        this.quantity = quantity;
        this.date = date;
        this.collector = collector;
        this.product = product;
    }
    @Override
    public String toString(){
        return String.format("assembling [id = %d, quantity = %d, date = %s, collector_id = %d, product_id = %d]", id, quantity,
                date, collector.getId(), product.getId());
    }
}
