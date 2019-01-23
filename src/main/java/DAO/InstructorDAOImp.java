package DAO;

import POJO.Client;
import POJO.Instructor;
import POJO.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InstructorDAOImp implements InstructorDAO {

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    public void save(Instructor instructor) {
        instructor.getUser().setPassword(User.hashPassword(instructor.getUser().getPassword()));
        getSession().save(instructor);
    }

    public List<Instructor> list() {
        List<Instructor> list = getSession().createQuery("from Instructor", Instructor.class).list();
        List<Instructor> listActive = new ArrayList<>();
        for(Instructor i : list){
            if(i.getUser().isActive())
                listActive.add(i);
        }
        return listActive;
    }

    public Instructor getInstructorById(long idinstructor) {
        return getSession().createQuery("from Instructor where id = :idinstructor", Instructor.class).setParameter("idinstructor", idinstructor).getSingleResult();
    }

    public Instructor getInstructorByUserId(long idinstructor) {
        return getSession().createQuery("from Instructor where user_id = :idinstructor", Instructor.class).setParameter("idinstructor", idinstructor).getSingleResult();
    }



    public List<Client> getClients(long idinstructor) {
        List<Client> list = getSession().createQuery("from Client where instructor.id = :idinstructor", Client.class).setParameter("idinstructor", idinstructor).list();
        List<Client> listActive = new ArrayList<>();
        for(Client c : list){
            if(c.getUser().isActive())
                listActive.add(c);
        }
        return listActive;
    }

    public void update(Instructor instructor){
        getSession().update(instructor);
    }
}
