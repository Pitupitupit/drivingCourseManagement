package POJO;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name="sms")
public class SMS {
    public  SMS(){};

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    @Column(name="id", updatable = false, nullable = false, unique = true)
    private Long id;

    @Column(name="text")
    private String text;

    @Column(name="telephone")
    private String telephone;

    @Column(name="sent")
    private Boolean sent;

    @OneToOne(
		optional=false,
		cascade={CascadeType.ALL}
	)
	@JoinColumn(name="drive", referencedColumnName="id", nullable=false)
    private Drive drive;

    @Column(name="iddrive")
    private long iddrive;

    @Column(name="myTimestamp")
    private Timestamp myTimestamp;

    public SMS(String text, String telephone, Boolean sent, Drive drive, long iddrive, Timestamp myTimestamp) {
        this.text = text;
        this.telephone = telephone;
        this.sent = sent;
        this.drive = drive;
        this.iddrive = iddrive;
        this.myTimestamp = myTimestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Boolean isSent() {
        return sent;
    }

    public void setSent(Boolean sent) {
        this.sent = sent;
    }

    public Drive getDrive() {
        return drive;
    }

    public void setDrive(Drive drive) {
        this.drive = drive;
    }

    public long getIddrive() {
        return iddrive;
    }

    public void setIddrive(long iddrive) {
        this.iddrive = iddrive;
    }

    public Timestamp getMyTimestamp() {
        return myTimestamp;
    }

    public void setMyTimestamp(Timestamp myTimestamp) {
        this.myTimestamp = myTimestamp;
    }
}
