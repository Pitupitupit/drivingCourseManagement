package POJO;

import javax.persistence.*;

@Entity
@Table(name="clients")
public class Client {

    public Client(){};

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id", updatable = false, nullable = false, unique = true)
    private Long id;

    @javax.persistence.OneToOne(
		optional=false,
		cascade={CascadeType.ALL}
	)
	@javax.persistence.JoinColumn(name="user_id", referencedColumnName="id", nullable=false)
    private User user;

    @javax.persistence.OneToOne(
		optional=false,
		cascade={CascadeType.ALL}
	)
	@javax.persistence.JoinColumn(name="instructor", referencedColumnName="id", nullable=false)
    private Instructor instructor;

    @Column(name="idinstructor")
    private Long idinstructor;

    @Column(name="category")
    private String category;

    public Client(User user, Long idinstructor, Instructor instructor, String category) {
        this.user = user;
        this.idinstructor = idinstructor;
        this.instructor = instructor;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getIdinstructor() {
        return idinstructor;
    }

    public void setIdinstructor(Long idinstructor) {
        this.idinstructor = idinstructor;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
