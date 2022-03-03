package allPaks.models;


import javax.persistence.*;

@Entity
@Table(name = "collectors")
public class Collector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "experience")
    private int experience;
    @ManyToOne
    @JoinColumn(name = "qualification_id")
    private Qualification qualification;

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

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Qualification getQualification() {
        return qualification;
    }

    public void setQualification(Qualification qualification) {
        this.qualification = qualification;
    }
    public Collector(){}
    public Collector(String name, int experience, Qualification qualification){
        this.name = name;
        this.experience = experience;
        this.qualification = qualification;
    }
    @Override
    public String toString(){
        return String.format("product [id = %d, name = %s, experience = %d, qualification_id = %d]", id, name, experience, qualification.getId());
    }
}
