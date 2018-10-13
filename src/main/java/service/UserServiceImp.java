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
}
