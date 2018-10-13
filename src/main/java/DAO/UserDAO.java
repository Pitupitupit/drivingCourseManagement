package DAO;

import POJO.User;

import java.util.List;

public interface UserDAO {
    public void save(User user);
    public List<User> list();
}
