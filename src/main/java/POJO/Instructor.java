package POJO;

import javax.persistence.*;

@Entity
@Table(name="instructors")
public class Instructor {

    public Instructor(){};

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id", updatable = false, nullable = false, unique = true)
    private Long id;

    @javax.persistence.OneToOne(
		optional=false,
		cascade={CascadeType.MERGE}
	)
	@javax.persistence.JoinColumn(name="user_id", referencedColumnName="id", nullable=false)
    private User user;

    @Column(name="categories")
    private String categories;

    public Instructor(User user, String categories) {
        this.user = user;
        this.categories = categories;
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

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }
}
