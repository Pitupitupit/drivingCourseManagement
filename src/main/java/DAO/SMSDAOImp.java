package DAO;

import POJO.SMS;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SMSDAOImp implements SMSDao{
    @Autowired
    SessionFactory sessionFactory;

    protected Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    public void save(SMS sms) {
        getSession().merge(sms);
    }

    public List<SMS> list() {
        List<SMS> list = sessionFactory.getCurrentSession().
                createQuery("from SMS", SMS.class).list();
        return list;
    }

    public List<SMS> listCheckTime(int minutesBefore) {

        List<SMS> list = sessionFactory.getCurrentSession().
                createQuery("from SMS", SMS.class).list();

        List<SMS> lastListSMS = new ArrayList<>();
        for(SMS sms : list){
            //
            System.out.println("Timestamp w esemesie: "+sms.getMyTimestamp());
            System.out.println("Timestamp w esemesie milis: "+sms.getMyTimestamp().getTime());
            //System.out.println("RAZ:"+sms.getMyTimestamp().getTime());
            long x = (System.currentTimeMillis()-(long)Math.pow(10,3)*60*60);
            System.out.println("current Time milis - godzina:"+x);
            //if((sms.getMyTimestamp().getTime() > (new Timestamp(System.currentTimeMillis()).getTime()-Math.pow(10,3)*60) && !sms.isSent())){
            if((System.currentTimeMillis() > sms.getMyTimestamp().getTime()) && !sms.isSent()){
                System.out.println("jest jaki? sms do wys?ania!");
                lastListSMS.add(sms);
            }
        }
        return lastListSMS;
        /*String sql = "SELECT * from sms where myTimestamp > DATE_SUB(NOW(), INTERVAL 1 MINUTE)";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.addEntity(SMS.class);
        List results = query.list();*/

    }


    public void updateSent(SMS sms) {
        getSession().update(sms);
    }
}
