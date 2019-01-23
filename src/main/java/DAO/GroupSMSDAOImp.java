package DAO;

import POJO.GroupSMS;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class GroupSMSDAOImp implements GroupSMSDAO {

    @Autowired
    SessionFactory sessionFactory;

    public List<GroupSMS> list() {
        return sessionFactory.getCurrentSession().createQuery("from GroupSMS", GroupSMS.class).list();
    }

    public void save(GroupSMS groupSMS) {
        groupSMS.setMyTimeStamp(new Timestamp(System.currentTimeMillis()));
        sessionFactory.getCurrentSession().save(groupSMS);
    }
}
