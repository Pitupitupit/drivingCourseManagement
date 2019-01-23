package POJO;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="groupsms")
public class GroupSMS {


    public GroupSMS(){};

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    @Column(name="id", updatable = false, nullable = false, unique = true)
    private Long id;

    @javax.persistence.OneToOne(
		optional=false,
		cascade={CascadeType.ALL}
	)
	@javax.persistence.JoinColumn(name="user_id", referencedColumnName="id", nullable=false)
    private User user;

    @Column(name="idUser")//który kiero/instr wys?a?
    private Long idUser;

    @Column(name="text")
    private String text;

    @Column(name="myTimestamp")//czas kiedy klikn??
    private Timestamp myTimeStamp;

    @Column(name="type") //do kogo(all/ins/klie/klie instruktora)
    private String type;

    public GroupSMS(User user, Long idUser, String text, Timestamp myTimeStamp, String type) {
        this.user = user;
        this.idUser = idUser;
        this.text = text;
        this.myTimeStamp = myTimeStamp;
        this.type = type;
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

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getMyTimeStamp() {
        return myTimeStamp;
    }

    public void setMyTimeStamp(Timestamp myTimeStamp) {
        this.myTimeStamp = myTimeStamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
