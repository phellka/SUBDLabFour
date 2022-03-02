package allPaks.models;

import javax.persistence.*;

@Entity
@Table(name = "qualifications")
public class qualification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "category")
    private int category;
    @Column(name = "name")
    private String name;
    @Column(name = "salary")
    private int salary;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
    public qualification(){}
    public qualification(int category, String name, int salary){
        this.category = category;
        this.name = name;
        this.salary = salary;
    }
    @Override
    public String toString(){
        return String.format("qualification [id = %d, category = %d, name = %s, salary = %d]", id, category, name, salary);
    }
}
