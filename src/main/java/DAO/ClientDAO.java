package DAO;

import POJO.Client;

import java.util.List;

public interface ClientDAO {
    public void save(Client client);
    public Client getClientByUsername(String username);
    public List<Client> list();
    public void update(Client client);
    Client getClientByClientId(long id);
}
