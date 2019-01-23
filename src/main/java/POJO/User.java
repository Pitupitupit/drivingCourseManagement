package POJO;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="users")
public class User {

    public User(){};

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id", updatable = false, nullable = false, unique = true)
    private Long id;

    @NotNull
    @Size(min=3)
    @Column(name="username", unique = true)
    private String username;

    @NotNull
    @Size(min=3)
    @Column(name="password")
    private String password;

    @Column(name="enabled")
    private boolean enabled = true;

    @Column(name="telephone")
    private String telephone;

    @Column(name="email")
    private String email;

    @Column(name="firstname")
    private String firstname;

    @Column(name="lastname")
    private String lastname;

    @Column(name="role")
    private String role;

    @Column(name="active")
    private boolean active=true;

    public User(@NotNull @Size(min = 3) String username, @NotNull @Size(min = 3) String password, boolean enabled, String telephone, String email, String firstname, String lastname, String role, boolean active) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.telephone = telephone;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
        this.active = active;
    }

    public static String hashPassword(String password){
        return new BCryptPasswordEncoder().encode(password);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
