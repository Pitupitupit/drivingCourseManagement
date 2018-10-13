package POJO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="User")
public class User {

    public User(){};

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @NotNull
    @Column(name="id", updatable = false, nullable = false)
    private Long id;

    @NotNull
    @Size(min=3)
    @Column(name="firstName")
    private String firstName;

    @NotNull
    @Size(min=3)
    @Column(name="lastName")
    private String lastName;

    public User(String firstName, String lastName){
        this.firstName=firstName;
        this.lastName=lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
