package service;

import DAO.ClientDAO;
import POJO.Client;
import POJO.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClientServiceImp implements ClientService {
    @Autowired
    ClientDAO clientDAO;

    public void addClient(Client client) {
        clientDAO.save(client);
    }

    public Client getClientByUsername(String username) {
        return clientDAO.getClientByUsername(username);
    }

    public List<Client> list() {
        return clientDAO.list();
    }

    public void update(Client client){
        clientDAO.update(client);
    }

    public Client getClientByClientId(long id) {
        return clientDAO.getClientByClientId(id);
    }
}
