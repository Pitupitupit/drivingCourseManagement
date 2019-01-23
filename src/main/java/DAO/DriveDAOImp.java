package DAO;

import POJO.Client;
import POJO.Drive;
import POJO.SMS;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DriveDAOImp implements DriveDAO {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    SMSDao smsDao;

    public List<Drive> listDrivesOfClient(long clientId) {
        try{
            List<Drive> list = sessionFactory.getCurrentSession().
                    createQuery("from Drive where idclient = :clientId order by myTimestamp asc", Drive.class).setParameter("clientId", clientId).list();
            for(Drive d : list){
                d.setDate(new Date(d.getMyTimestamp().getTime()));
            }

            return list;
        } catch(NoResultException nre) {
//
        }
        return null;
    }


    public List<Drive> listDrivesOfInstructor(long instructorId) {
        System.out.println("parametr w listDrivesOfInstructor: "+instructorId);
        List<Drive> list = sessionFactory.getCurrentSession().
                createQuery("from Drive where idinstructor = :instructorId ORDER BY myTimestamp ASC", Drive.class).setParameter("instructorId", instructorId).list();
        for(Drive d : list){
            d.setDate(new Date(d.getMyTimestamp().getTime()));
        }
        return list;
    }

    public Drive save(Drive drive) {
        /*System.out.println(drive.getDate().toString()+" "+drive.getStart().toString());
        drive.setMyTimestamp(Timestamp.valueOf(drive.getDate().toString()+" "+drive.getStart().toString()));
        drive.setDone(Boolean.FALSE);

        String duration = Drive.calculateDifferenceBetweenTime(drive.getStart(), drive.getStop());
        Timestamp t = new Timestamp((drive.getMyTimestamp().getTime())-(long)Math.pow(10,3)*60*60);
        System.out.println("t.tostring: "+t.toString());
        System.out.println("timestamp t: "+t);

        drive.setDuration(Drive.calculateDifferenceBetweenTime(drive.getStart(),drive.getStop()));
        Drive newdrive = (Drive) sessionFactory.getCurrentSession().merge(drive);
        System.out.println("Nowe id to:"+newdrive.getId());

        SMS sms = new SMS(
                "Witaj "+drive.getClient().getUser().getFirstname()+". Przypomnienie o nauce jazdy. Data: "+drive.getDate()+", godzina: "+drive.getStart()+", czas trwania: "+duration+". Tel. instruktora: "+drive.getInstructor().getUser().getTelephone(),
                drive.getClient().getUser().getTelephone(),false,newdrive,newdrive.getId(), t);
        sms.setSent(Boolean.FALSE);

        System.out.println("sms.getMytimestamp: "+sms.getMyTimestamp());
        System.out.println("sms.getMytimestamp.toString: "+sms.getMyTimestamp().toString());
        System.out.println("sms.getSENT: "+sms.isSent());

        smsDao.save(sms);*/
        return (Drive) sessionFactory.getCurrentSession().merge(drive);
    }

    @Override
    public void update(Drive drive) {
        sessionFactory.getCurrentSession().update(drive);
    }

    public List<Drive> listUndone() {
        List<Drive> list = sessionFactory.getCurrentSession().
                createQuery("from Drive where done=false", Drive.class).list();
        return list;
    }

    public List<Integer> sumDurationClient(long clientId) {
        int hours=0;
        int minutes=0;
        List<Drive> list = listDrivesOfClient(clientId);
        for(Drive d : list){
            if(d.getDone()) {
                hours = hours + Drive.calculateDifferenceBetweenTimeList(d.getStart(), d.getStop()).get(0);
                minutes = minutes + Drive.calculateDifferenceBetweenTimeList(d.getStart(), d.getStop()).get(1);

            }
        }
        int x = (int)Math.floor(hours/60);
        int y = minutes%60;
        List<Integer> listInteger = new ArrayList<>();
        listInteger.add(hours+x);
        listInteger.add(y);

        return listInteger;
    }

    public List<Integer> sumDurationInstructor(long instructorId) {
        int hours=0;
        int minutes=0;
        List<Drive> list = listDrivesOfInstructor(instructorId);
        for(Drive d : list){
            if(d.getDone()) {
                hours = hours + Drive.calculateDifferenceBetweenTimeList(d.getStart(), d.getStop()).get(0);
                minutes = minutes + Drive.calculateDifferenceBetweenTimeList(d.getStart(), d.getStop()).get(1);

            }
        }
        int x = (int)Math.floor(hours/60);
        int y = minutes%60;
        List<Integer> listInteger = new ArrayList<>();
        listInteger.add(hours+x);
        listInteger.add(y);
        return listInteger;
    }
}
