package DAO;

import POJO.User;
import org.hibernate.SQLQuery;
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
        user.setPassword(User.hashPassword(user.getPassword()));
        System.out.println("hash: "+user.getPassword());
        getSession().save(user);
    }

    public List<User> list() {

        List<User> list = getSession().createQuery("from User where active = true", User.class).list();

        for(User u : list){
            System.out.println(u.getUsername());
        }

        return list;
    }

    public List<User> listByRole(String role) {
        List<User> list = getSession().createQuery("from User where role = :role and active = true", User.class).setParameter("role", role).list();

        for(User u : list){
            System.out.println(u.getUsername());
        }

        return list;
    }

    public User getUserById(long id) {
        User user = getSession().createQuery("from User where id = :id", User.class).setParameter("id", id).getSingleResult();
        return user;
    }

    public User getUserByUsername(String username) {
        return getSession().createQuery("from User where username = :username", User.class).setParameter("username", username).getSingleResult();
    }

    public void updateUser(User user) {
        getSession().merge(user);
    }

    public void setInactive(long id) {
        getSession().createSQLQuery("update users set active=false where id=:id").setParameter("id", id).executeUpdate();
    }
}
