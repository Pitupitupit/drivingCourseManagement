package POJO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Entity
@Table(name="drives")
public class Drive {
    public Drive(){};

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    @Column(name="id", updatable = false, nullable = false, unique = true)
    private Long id;

    @Column(name="\"date\"")
    private Date date;

    @Column(name="start")
    private Time start;

    @Column(name="stop")
    private Time stop;

    @Column(name="duration")
    private String duration;

    @Column(name="myTimestamp")
    private Timestamp myTimestamp;

    @javax.persistence.OneToOne(
		optional=false,
		cascade={CascadeType.ALL}
	)
	@javax.persistence.JoinColumn(name="client", referencedColumnName="id", nullable=false)
    private Client client;

    @Column(name="idclient")
    private Long idclient;

    @javax.persistence.OneToOne(
		optional=false,
		cascade={CascadeType.REFRESH}
	)
	@javax.persistence.JoinColumn(name="instructor", referencedColumnName="id", nullable=false)
    private Instructor instructor;

    @Column(name="idinstructor")
    private Long idinstructor;

    @Column(name="test")
    private String test;

    @Column(name="done")
    private Boolean done;

    public String startString;
    public String endString;

    public Drive(Date date, Time start, Time stop, String duration, Timestamp myTimestamp, Client client, Long idclient, Instructor instructor, Long idinstructor, String test, Boolean done, String startString, String endString) {
        this.date = date;
        this.start = start;
        this.stop = stop;
        this.duration = duration;
        this.myTimestamp = myTimestamp;
        this.client = client;
        this.idclient = idclient;
        this.instructor = instructor;
        this.idinstructor = idinstructor;
        this.test = test;
        this.done = done;
        this.startString = startString;
        this.endString = endString;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Long getIdclient() {
        return idclient;
    }

    public void setIdclient(Long idclient) {
        this.idclient = idclient;
    }

    public Long getIdinstructor() {
        return idinstructor;
    }

    public void setIdinstructor(Long idinstructor) {
        this.idinstructor = idinstructor;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public void setStart(Time start) {
        this.start = start;
    }

    public Time getStop() {
        return stop;
    }

    public void setStop(Time stop) {
        this.stop = stop;
    }

    public Time getStart() {
        return start;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getStartString() {
        return startString;
    }

    public void setStartString(String startString) {
        this.startString = startString;
    }

    public String getEndString() {
        return endString;
    }

    public void setEndString(String endString) {
        this.endString = endString;
    }

    public Timestamp getMyTimestamp() {
        return myTimestamp;
    }

    public void setMyTimestamp(Timestamp myTimestamp) {
        this.myTimestamp = myTimestamp;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public static String calculateDifferenceBetweenTime(Time t1, Time t2){
        LocalTime from = t1.toLocalTime();
        LocalTime to = t2.toLocalTime();
        //Duration d = Duration.between(from,to);
        long millis = Duration.between(from,to).toMillis();
        int minutes = (int) ((millis / (1000*60)) % 60);
        int hours   = (int) ((millis / (1000*60*60)) % 24);

        return Integer.toString(hours)+"h "+Integer.toString(minutes)+"min";
    }

    public static List<Integer> calculateDifferenceBetweenTimeList(Time t1, Time t2){
        LocalTime from = t1.toLocalTime();
        LocalTime to = t2.toLocalTime();
        //Duration d = Duration.between(from,to);
        long millis = Duration.between(from,to).toMillis();
        int minutes = (int) ((millis / (1000*60)) % 60);
        int hours   = (int) ((millis / (1000*60*60)) % 24);
        List<Integer> list = new ArrayList<>();
        list.add(hours);
        list.add(minutes);

        return list;
    }


}
