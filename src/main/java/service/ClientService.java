package service;

import POJO.Client;

import java.util.List;

public interface ClientService {
    void addClient(Client client);
    Client getClientByUsername(String username);
    List<Client> list();
    void update(Client client);
    Client getClientByClientId(long id);
}
