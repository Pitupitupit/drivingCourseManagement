package DAO;

import POJO.Client;
import POJO.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ClientDAOImp implements ClientDAO {
    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    public void save(Client client) {
        client.getUser().setPassword(User.hashPassword(client.getUser().getPassword()));
        System.out.println("hash: "+client.getUser().getPassword());
        getSession().save(client);
    }

    public Client getClientByUsername(String username) {
        return getSession().createQuery("from Client where user_id = (select id from User where username = :username)",
                Client.class).setParameter("username", username).getSingleResult();

    }

    public List<Client> list() {

        List<Client> list = getSession().createQuery("from Client c", Client.class).list();
        List<Client> listActive = new ArrayList<>();
        for(Client c : list){
            if(c.getUser().isActive())
                listActive.add(c);
        }
        return listActive;
    }

    public void update(Client client){
        getSession().update(client); //merge?
    }

    public Client getClientByClientId(long id) {
        return getSession().createQuery("from Client where id = :id",
                Client.class).setParameter("id", id).getSingleResult();
    }
}


