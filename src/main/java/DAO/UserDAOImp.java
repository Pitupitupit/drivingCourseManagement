package DAO;

import POJO.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImp implements UserDAO {
    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    public void save(User user) {
        getSession().save(user);
    }

    public List<User> list() {

        List<User> list = getSession().createQuery("from User", User.class).list();

        for(User u : list){
            System.out.println(u.getFirstName());
        }

        return list;
    }

}
