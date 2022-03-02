package allPaks.models;

import javax.persistence.*;

@Entity
@Table(name = "producttypes")
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "gostnumber")
    private int gostNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGostNumber() {
        return gostNumber;
    }

    public void setGostNumber(int gostNumber) {
        this.gostNumber = gostNumber;
    }
    public ProductType(){}
    public ProductType(String name, int gostNumber){
        this.name = name;
        this.gostNumber = gostNumber;
    }
    @Override
    public String toString(){
        return String.format("producttype [id = %d, name = %s, gostNumber = %d]", id, name, gostNumber);
    }
}
