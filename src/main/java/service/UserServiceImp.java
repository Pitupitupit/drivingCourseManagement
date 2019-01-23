package service;

import DAO.UserDAO;
import POJO.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImp implements UserService {
    @Autowired
    UserDAO userDAO;

    public void addUser(User user) {
        userDAO.save(user);
    }

    public List<User> list() {
        return userDAO.list();
    }

    public List<User> listByRole(String role) {
        return userDAO.listByRole(role);
    }

    public User getUserById(long id) {
        return userDAO.getUserById(id);
    }

    public User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    public void setInactive(long id){
        userDAO.setInactive(id);
    }


}
